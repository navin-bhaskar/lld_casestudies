package com.splitmoney.splitmoney.services;

import com.splitmoney.splitmoney.exceptions.UserNotFound;
import com.splitmoney.splitmoney.models.*;
import com.splitmoney.splitmoney.repositories.ExpenseRepository;
import com.splitmoney.splitmoney.repositories.UserExpenseRepository;
import com.splitmoney.splitmoney.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Transaction> settleUser(User usr) {
        List<Expense> expenses = expenseRepository.findAllByUserExpensesUser(usr);
        List<Transaction> allTransactions =  generateTransaction(expenses);
        return allTransactions.stream().filter(t ->
                Objects.equals(t.getOwedBy().getId(), usr.getId()) ||
                        Objects.equals(t.getOwedTo().getId(), usr.getId())).collect(Collectors.toList());
    }

    public List<Transaction> settleGroup(Group group) {
        List<Expense> expenses = group.getExpenses();
        return generateTransaction(expenses);
    }

    public List<Transaction> generateTransaction(List<Expense> expenses) {
        HashMap<Long, Integer> userExpenseMap = new HashMap<>();
        HashMap<Long, User> userCache = new HashMap<>();
        computeTotals(expenses, userExpenseMap, userCache);

        return getTransactions(userExpenseMap, userCache);
    }

    private static List<Transaction> getTransactions(
            HashMap<Long, Integer> userExpenseMap,
            HashMap<Long, User> userCache) {

        Comparator<UserExpense> cmp = (o1, o2) -> o1.getAmount() - o2.getAmount();
        PriorityQueue<UserExpense> maxq = new PriorityQueue<UserExpense>(50, cmp);
        PriorityQueue<UserExpense> minq = new PriorityQueue<UserExpense>(50, cmp);

        prepareHeaps(userExpenseMap, userCache, maxq, minq);
        return getTransactionList(maxq, minq);
    }

    private static List<Transaction> getTransactionList(PriorityQueue<UserExpense> maxq, PriorityQueue<UserExpense> minq) {
        List<Transaction> transactions = new ArrayList<>();
        while (maxq.size() > 0 && minq.size() > 0) {
            UserExpense maxAmtUser = maxq.poll();
            UserExpense minAmtUser = minq.poll();
            int diffAmt = maxAmtUser.getAmount() + minAmtUser.getAmount();
            Transaction t = getTransaction(maxAmtUser, minAmtUser);
            transactions.add(t);
            if ((maxAmtUser.getAmount() + minAmtUser.getAmount())  > 0 ){
                maxAmtUser.setAmount(diffAmt);
                maxq.add(maxAmtUser);
            } else {
                minAmtUser.setAmount(-diffAmt);
                minq.add(minAmtUser);
            }
        }
        return transactions;
    }

    private static Transaction getTransaction(UserExpense maxAmtUser, UserExpense minAmtUser) {
        Transaction t = new Transaction();
        t.setAmt(-minAmtUser.getAmount());
        t.setOwedBy(minAmtUser.getUser());
        t.setOwedTo(maxAmtUser.getUser());
        return t;
    }

    private static void prepareHeaps(
            HashMap<Long, Integer> userExpenseMap,
            HashMap<Long, User> userCache,
            PriorityQueue<UserExpense> maxq,
            PriorityQueue<UserExpense> minq) {
        for(Long usrId: userExpenseMap.keySet()) {
            int amt = userExpenseMap.get(usrId);
            UserExpense temp = new UserExpense();
            temp.setUser(userCache.get(usrId));
            temp.setAmount(amt);
            if (amt < 0) {
                minq.add(temp);
            } else if(amt > 0) {
                maxq.add(temp);
            }
        }
    }

    private static void computeTotals(
            List<Expense> expenses,
            HashMap<Long, Integer> userExpenseMap,
            HashMap<Long, User> userCache) {
        for(Expense exp: expenses) {
            for(UserExpense usrExp: exp.getUserExpenses()) {
                Long userId = usrExp.getUser().getId();
                if(!userExpenseMap.containsKey(userId)) {
                    userExpenseMap.put(userId, 0);
                    userCache.put(userId, usrExp.getUser());
                }
                int curAmt = userExpenseMap.get(userId);
                if(usrExp.getUsrExpType() == UserExpenseType.WHO_PAID) {
                    userExpenseMap.put(userId, curAmt + usrExp.getAmount());
                } else {
                    userExpenseMap.put(userId, curAmt - usrExp.getAmount());
                }
            }
        }
    }


}
