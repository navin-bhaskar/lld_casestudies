package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.controllers.ExpenseController;
import com.splitmoney.splitmoney.services.ExpenseService;
import com.splitmoney.splitmoney.services.UserService;
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
        return words[1].equalsIgnoreCase(command);
        // TODO: add more check
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
        int totalAmountPaid = getTotalAmountPaid(paidAmounts);

        List<Integer> owedAmounts = getOwedAmounts(totalAmountPaid);
        String desc = getDescription();

        AddExpenseRequestDto requestDto = getRequestDto(
                createdByAlias,
                totalAmountPaid,
                usersAlias,
                expenseType,
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
            String expenseType,
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

        if (expenseType.equalsIgnoreCase(multiPayType)) {
            // In multipay the creator also owes amount
            newList.addAll(usersAlias);
            usersWhoPaid = newList;
            usersWhoHadToPay = newList;
        } else {
            usersWhoPaid = newList;
            usersWhoHadToPay = usersAlias;
        }

        requestDto.setUsersWhoPaid(usersWhoPaid);
        requestDto.setPaidAmount(paidAmounts);

        requestDto.setUsersWhoHadToPay(usersWhoHadToPay);
        requestDto.setOwedAmount(owedAmounts);

        requestDto.setDescription(desc);
        return requestDto;
    }

    private List<Integer> getOwedAmounts(int totalAmountPaid) {
        List<Integer> owedAmount = new ArrayList<>();

        String distributionType = getDistributionType();
        if(distributionType.equalsIgnoreCase(distributionTypeEqual)) {
            owedAmount = getInts();
        } else if(distributionType.equalsIgnoreCase(distributionTypePercent)) {
            List<Integer> percents = getInts();
            for (Integer percent : percents) {
                int curAmt = (int) ((percent / 100.0) * totalAmountPaid);
                owedAmount.add(curAmt);
            }
        }
//        } else {
//            // TODO: Unknown option, raise error
//        }
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
                Format: TBD""";
    }
}
