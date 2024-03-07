package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.controllers.ExpenseController;
import com.splitmoney.splitmoney.exceptions.InvalidInputException;
import dtos.AddExpenseRequestDto;
import dtos.AddExpenseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Expense implements Command {
    final private ExpenseController expenseController;
    private final String command = "expense";
    private String[] words;

    final private String multiPayType = "multipay";
    final private String iPayType = "ipay";
    final private String desc = "desc";
    final private String distributionTypeEqual = "equal";
    final private String distributionTypePercent = "percent";
    final private String distributionTypeExact = "exact";
    final private String distributionTypeRatio = "ratio";

    private int parseIdx=0;
    private Set<String> allPayTypes;

    @Autowired
    Expense(ExpenseController expenseController) {
        this.expenseController = expenseController;
        addPayTypes();
    }

    private void addPayTypes() {
        allPayTypes = new HashSet<>();
        allPayTypes.add(multiPayType);
        allPayTypes.add(iPayType);
    }
    @Override
    public boolean check(String cmdStr) {
        words = cmdStr.split(" ");
        parseIdx = 0;
        return words[1].equalsIgnoreCase(command);
    }

    private String getCreatedBy() {
        return words[parseIdx++];
    }

    private List<String> getUsers() {
        List<String> users = new ArrayList<>();
        while (parseIdx < words.length) {
            String curWord = words[parseIdx];
            if (allPayTypes.contains(curWord.toLowerCase())) {
                break;
            }
            users.add(words[parseIdx++]);
        }
        return users;
    }

    public String getExpenseType() {
        return words[parseIdx++];
    }

    public String getDistributionType() {
        return words[parseIdx++];
    }

    public List<Integer> getInts() {
        List<Integer> amounts = new ArrayList<>();

        while (parseIdx < words.length) {
            try {
                amounts.add(Integer.parseInt(words[parseIdx]));
                parseIdx++;
            } catch (NumberFormatException ex) {
                break;
            }
        }
        return amounts;
    }

    public String getDescription() {
        StringBuilder res = new StringBuilder();
        // TODO: Check for the 'DESC'
        parseIdx++;
        while (parseIdx < words.length) {
            res.append(words[parseIdx++]).append(" ");
        }
        return res.toString();
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        String createdByAlias = getCreatedBy();
        parseIdx++;         // skip 'expense'

        List<String> usersAlias = getUsers();
        String expenseType = getExpenseType();
        List<Integer> paidAmounts = getInts();

        if(expenseType.equalsIgnoreCase(iPayType) && paidAmounts.size() != 1) {
            System.out.println("On ipay option, only a single amount, paid by user is expected");
            return;
        }

        int totalAmountPaid = getTotalAmountPaid(paidAmounts);
        int numOfPeople = usersAlias.size()+1; // +1 for the expense creator
        List<Integer> owedAmounts;
        try{
            owedAmounts = getOwedAmounts(totalAmountPaid, numOfPeople);
        } catch (InvalidInputException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        String desc = getDescription();

        AddExpenseRequestDto requestDto = getRequestDto(
                createdByAlias,
                totalAmountPaid,
                usersAlias,
                paidAmounts,
                owedAmounts,
                desc);
        AddExpenseResponseDto resp = expenseController.addExpense(requestDto);
        System.out.println(resp.getMessage());
    }

    private AddExpenseRequestDto getRequestDto(
            String createdByAlias,
            int totalAmountPaid,
            List<String> usersAlias,
            List<Integer> paidAmounts,
            List<Integer> owedAmounts,
            String desc) {
        AddExpenseRequestDto requestDto = new AddExpenseRequestDto();

        requestDto.setCreatedBy(createdByAlias);
        requestDto.setTotalAmount(totalAmountPaid);
        List<String > newList = new ArrayList<>();
        // In all cases creator is involved
        newList.add(createdByAlias);

        List<String> usersWhoPaid;
        List<String> usersWhoHadToPay;
        newList.addAll(usersAlias);
        usersWhoPaid = newList;
        usersWhoHadToPay = newList;


        requestDto.setUsersWhoPaid(usersWhoPaid);
        requestDto.setPaidAmount(paidAmounts);

        requestDto.setUsersWhoHadToPay(usersWhoHadToPay);
        requestDto.setOwedAmount(owedAmounts);

        requestDto.setDescription(desc);
        return requestDto;
    }

    private List<Integer> getOwedAmounts(int totalAmountPaid, int people) {
        // TODO: Make this as startergy to get owed amounts
        List<Integer> owedAmount = new ArrayList<>();

        String distributionType = getDistributionType();
        if(distributionType.equalsIgnoreCase(distributionTypeEqual)) {
            // Divide the amount equally amount
            int dist = totalAmountPaid/people;
            int rem = totalAmountPaid%people;
            for(int i=0; i<people; i++) {
                owedAmount.add(dist);
            }
            int i = 0;
            while (rem > 0) {
                // Distribute the remainder
                owedAmount.set(i, owedAmount.get(i)+1);
                rem -= 1;
                i += 1;
            }
            
        } else if(distributionType.equalsIgnoreCase(distributionTypePercent)) {
            List<Integer> percents = getInts();
            int runningPercent=0;
            for (Integer percent : percents) {
                runningPercent += percent;
                int curAmt = (int) ((percent / 100.0) * totalAmountPaid);
                owedAmount.add(curAmt);
            }
            if (runningPercent != 100) {
                throw new InvalidInputException("The percents do no add up");
            }
        } else if (distributionType.equalsIgnoreCase(distributionTypeExact)) {
            owedAmount = getInts();
        } else {
            throw new InvalidInputException("Unknown option for amount distribution");
        }
        return owedAmount;
    }

    private int getTotalAmountPaid(List<Integer> paidAmounts) {

        int totalAmountPaid = 0;
        for(Integer amt: paidAmounts) {
            totalAmountPaid += amt;
        }
        return totalAmountPaid;
    }

    @Override
    public String help() {
        return """
                Expense: Add an expense
                Allows multiple formats
                u1 Expense u2 u3 u4 iPay 1000 Equal Desc Last night dinner
                # u1 created an expense with u2, u3, u4 and paid 1000 everyone owes equal amount
                u1 Expense u2 u3 iPay 1000 Percent 20 30 50 Desc House rent
                # U1 created an expense with u2 u3 and paid 1000 and u1 owes 20%, u2 30% and u3 50% of 1000
                u1 Expense u2 u3 MultiPay 100 300 200 Equal Desc Lunch at office
                # U1 created expense with u2 and u3 where u1 paid 100, u2 300 and u3 200 and everyone owes equal amount""";
    }
}
