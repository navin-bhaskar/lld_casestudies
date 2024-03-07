package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.controllers.ExpenseController;
import com.splitmoney.splitmoney.models.Transaction;
import dtos.ResponseStatus;
import dtos.SettleGroupRequestDto;
import dtos.SettleUserRequestDto;
import dtos.SettleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUp implements Command{
    private final String command = "settleup";
    private final ExpenseController expenseController;
    private String[] words;
    @Autowired
    SettleUp(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean check(String cmdStr) {
        words = cmdStr.split(" ");
        return (words.length == 2 || words.length == 3) && words[1].equalsIgnoreCase(command);
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        SettleResponseDto resp;
        String usr = words[0];
        if (words.length == 2) {
            SettleUserRequestDto req = new SettleUserRequestDto();
            req.setUser(usr);
            resp = expenseController.settleUser(req);
        } else {
            SettleGroupRequestDto req = new SettleGroupRequestDto();
            req.setRequestedBy(usr);
            req.setGroup(words[2]);
            resp = expenseController.settleGroup(req);
        }

        if(resp.getStatus() != ResponseStatus.SUCCESS) {
            System.out.println(resp.getMessage());
        } else {
            System.out.println("-------------Transactions------------");
            for(Transaction t: resp.getTransactions()) {
                System.out.printf(
                        "User %s pays %d to %s\n",
                        t.getOwedBy().getName(),
                        t.getAmt(),
                        t.getOwedTo().getName());
            }
        }
    }

    @Override
    public String help() {
        return "";
    }
}
