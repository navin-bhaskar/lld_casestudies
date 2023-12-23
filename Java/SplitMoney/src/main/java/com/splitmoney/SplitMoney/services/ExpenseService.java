package com.splitmoney.splitmoney.services;

import com.splitmoney.splitmoney.models.Expense;
import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.models.UserExpense;
import com.splitmoney.splitmoney.models.UserExpenseType;
import com.splitmoney.splitmoney.repositories.ExpenseRepository;
import com.splitmoney.splitmoney.repositories.UserExpenseRepository;
import com.splitmoney.splitmoney.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {
    private Map<String, User> users;
    private Map<String, UserExpense> userExp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserExpenseRepository userExpenseRepository;

    public ExpenseService(Map<String, User> usersMap) {
        this.users = usersMap;
        userExp = new HashMap<>();

        for (String user: users.keySet()){
            UserExpense usrExp = new UserExpense();
            usrExp.setUser(users.get(user));
            userExp.put(user, new UserExpense());
        }
    }

    public void addExpense(
            User createdBy,
            int amtPaid,
            String description,
            List<UserExpense> owedToUsers,
            List<UserExpense> owedByUsers) {
        Expense expense = new Expense();
        expense.setCreatedBy(createdBy);
        expense.setAmount(amtPaid);
        expense.setDescription(description);

        expenseRepository.save(expense);

        for(UserExpense exp: owedToUsers) {
            exp.setExpense(expense);
            exp.setUsrExpType(UserExpenseType.WHO_PAID);
            userExpenseRepository.save(exp);
        }

        for(UserExpense exp: owedByUsers) {
            exp.setExpense(expense);
            exp.setUsrExpType(UserExpenseType.WHO_HAD_TO_PAY);
            userExpenseRepository.save(exp);
        }

    }



    public void generateTransaction() {
        System.out.println("Final totals: ");
        Comparator<UserExpense> cmp = new Comparator<>() {
            @Override
            public int compare(UserExpense o1, UserExpense o2) {
                return o1.getExpense().getAmount() - o2.getExpense().getAmount();
            }
        };
        PriorityQueue<UserExpense> maxq = new PriorityQueue<UserExpense>(50, cmp);

        PriorityQueue<UserExpense> minq = new PriorityQueue<UserExpense>(50, cmp);
        for(String usr: userExp.keySet()) {
            UserExpense curUserExp = userExp.get(usr);
            String userName = users.get(usr).getName();
            int amt = userExp.get(usr).getExpense().getAmount();
            System.out.println("User " + userName + "("+ usr + ")="+ amt);
            if (amt < 0) {
                minq.add(curUserExp);
            } else {
                maxq.add(curUserExp);
            }
        }

        System.out.println("----------Transactions----------------");
        while (maxq.size() > 0 && minq.size() > 0) {
            UserExpense maxAmtUser = maxq.poll();
            UserExpense minAmtUser = minq.poll();
            int diffAmt = maxAmtUser.getExpense().getAmount() + minAmtUser.getExpense().getAmount();
            String msg =  minAmtUser.getUser().getName() +
                    " pays " +
                    maxAmtUser.getUser().getName() +
                    " amount " + -minAmtUser.getExpense().getAmount();
            System.out.println(msg);
            if ((maxAmtUser.getExpense().getAmount() + minAmtUser.getExpense().getAmount())  > 0 ){
                maxAmtUser.getExpense().setAmount(diffAmt);
                maxq.add(maxAmtUser);
            } else {
                minAmtUser.getExpense().setAmount(-diffAmt);
                minq.add(minAmtUser);
            }
        }

    }


}
