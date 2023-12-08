package Services;

import models.Expense;
import models.User;
import models.UserExpense;

import java.util.*;

public class ExpenseHandler {
    private Map<String, User> users;
    private Map<String, UserExpense> userExp;

    public ExpenseHandler(Map<String, User> usersMap) {
        this.users = usersMap;
        userExp = new HashMap<>();

        for (String user: users.keySet()){
            userExp.put(user, new UserExpense(users.get(user)));
        }
    }

    public void addExpense(String usr, int amt) {
        UserExpense uExp = userExp.get(usr);
        int finalAmount = uExp.getAmount() + amt;
        uExp.setAmount(finalAmount);
    }

    public void generateTransaction() {
        System.out.println("Final totals: ");
        Comparator<UserExpense> cmp = new Comparator<UserExpense>() {
            @Override
            public int compare(UserExpense o1, UserExpense o2) {
                return o1.getAmount() - o2.getAmount();
            }
        };
        PriorityQueue<UserExpense> maxq = new PriorityQueue<UserExpense>(50, cmp);

        PriorityQueue<UserExpense> minq = new PriorityQueue<UserExpense>(50, cmp);
        for(String usr: userExp.keySet()) {
            UserExpense curUserExp = userExp.get(usr);
            String userName = users.get(usr).getName();
            int amt = userExp.get(usr).getAmount();
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
            int diffAmt = maxAmtUser.getAmount() + minAmtUser.getAmount();
            String msg =  minAmtUser.getUser().getName() +
                    " pays " +
                    maxAmtUser.getUser().getName() +
                    " amount " + -minAmtUser.getAmount();
            System.out.println(msg);
            if ((maxAmtUser.getAmount() + minAmtUser.getAmount())  > 0 ){
                maxAmtUser.setAmount(diffAmt);
                maxq.add(maxAmtUser);
            } else {
                minAmtUser.setAmount(-diffAmt);
                minq.add(minAmtUser);
            }
        }

    }


}
