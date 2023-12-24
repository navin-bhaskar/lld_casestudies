package com.splitmoney.splitmoney.commands;

import com.splitmoney.splitmoney.controllers.ExpenseController;
import com.splitmoney.splitmoney.models.Transaction;
import dtos.ResponseStatus;
import dtos.SettleUserRequestDto;
import dtos.SettleUserResponseDto;
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
        // TODO: Add more checks
        words = cmdStr.split(" ");
        return words[1].equalsIgnoreCase(command);
    }

    @Override
    public void execute(String cmdStr) {
        words = cmdStr.split(" ");
        String usr = words[0];
        // TODO: add settle for group
        SettleUserRequestDto req = new SettleUserRequestDto();
        req.setUser(usr);
        SettleUserResponseDto resp = expenseController.settleUser(req);

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
