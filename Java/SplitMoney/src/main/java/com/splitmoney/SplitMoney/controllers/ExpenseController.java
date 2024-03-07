package com.splitmoney.splitmoney.controllers;

import com.splitmoney.splitmoney.models.*;
import com.splitmoney.splitmoney.services.ExpenseService;
import com.splitmoney.splitmoney.services.GroupService;
import com.splitmoney.splitmoney.services.UserService;
import dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseController {
    private ExpenseService expenseService;
    private UserService userService;
    private GroupService groupService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService, GroupService groupService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.groupService = groupService;
    }

    public AddExpenseResponseDto addExpense(AddExpenseRequestDto request) {
        AddExpenseResponseDto resp = new AddExpenseResponseDto();

        String createdBy = request.getCreatedBy();
        User createdByUser;
        Optional<User> usr = userService.findByAlias(createdBy);
        if (usr.isEmpty()) {
            resp.setMessage(UserService.getUsrNotFoundMsg(createdBy));
            resp.setStatus(ResponseStatus.FAILURE);
            return resp;
        }
        createdByUser = usr.get();


        List<String> usersWhoPaidAlis = request.getUsersWhoPaid();
        List<Integer> paidAmts = request.getPaidAmount();
        List<UserExpense> usersWhoPaid = new ArrayList<>();

        for(int i=0; i<usersWhoPaidAlis.size(); i++) {
            String usrAlias = usersWhoPaidAlis.get(i);
            int paidAmt = paidAmts.get(i);

            Optional<User> usrOptional = userService.findByAlias(usrAlias);
            if (usrOptional.isEmpty()) {
                resp.setMessage(UserService.getUsrNotFoundMsg(usrAlias));
                resp.setStatus(ResponseStatus.FAILURE);
                return resp;
            }
            UserExpense tmpUsrExp = new UserExpense();
            tmpUsrExp.setUser(usrOptional.get());
            tmpUsrExp.setAmount(paidAmt);
            tmpUsrExp.setUsrExpType(UserExpenseType.WHO_PAID);

            usersWhoPaid.add(tmpUsrExp);
        }

        List<String> userWhoHadToPay = request.getUsersWhoHadToPay();
        List<Integer> owedAmounts = request.getOwedAmount();
        List<UserExpense> usersWhoHadToPay = new ArrayList<>();

        for(int i = 0; i<userWhoHadToPay.size(); i++) {
            String usrAlias = userWhoHadToPay.get(i);
            int paidAmt = owedAmounts.get(i);

            Optional<User> usrOptional = userService.findByAlias(usrAlias);
            if (usrOptional.isEmpty()) {
                resp.setMessage(UserService.getUsrNotFoundMsg(usrAlias));
                resp.setStatus(ResponseStatus.FAILURE);
                return resp;
            }
            UserExpense tmpUsrExp = new UserExpense();
            tmpUsrExp.setUser(usrOptional.get());
            tmpUsrExp.setAmount(paidAmt);
            tmpUsrExp.setUsrExpType(UserExpenseType.WHO_HAD_TO_PAY);

            usersWhoHadToPay.add(tmpUsrExp);
        }

        expenseService.addExpense(
                createdByUser,
                request.getTotalAmount(),
                request.getDescription(),
                usersWhoPaid,
                usersWhoHadToPay);
        resp.setStatus(ResponseStatus.SUCCESS);
        resp.setMessage("Added expense successfully");
        return resp;
    }

    public SettleResponseDto settleUser(SettleUserRequestDto request) {
        SettleResponseDto resp = new SettleResponseDto();
        Optional<User> usr = userService.findByAlias(request.getUser());
        List<Transaction> transactions;

        if(usr.isEmpty()) {
            resp.setMessage(UserService.getUsrNotFoundMsg(request.getUser()));
            resp.setStatus(ResponseStatus.FAILURE);
            return resp;
        }
        transactions = this.expenseService.settleUser(usr.get());
        resp.setStatus(ResponseStatus.SUCCESS);
        resp.setTransactions(transactions);
        return resp;
    }

    public SettleResponseDto settleGroup(SettleGroupRequestDto request) {
        SettleResponseDto resp = new SettleResponseDto();
        Optional<User> requestedUsr = userService.findByAlias(request.getRequestedBy());
        Optional<Group> group;
        List<Transaction> transactions;

        if(requestedUsr.isEmpty()) {
            resp.setMessage(UserService.getUsrNotFoundMsg(request.getRequestedBy()));
            resp.setStatus(ResponseStatus.FAILURE);
        }

        group = groupService.findByAlias(request.getGroup());
        if(group.isEmpty()) {
            resp.setMessage(GroupService.getGrpNotFoundMsg(request.getGroup()));
            resp.setStatus(ResponseStatus.FAILURE);
            return resp;
        }

        transactions = expenseService.settleGroup(group.get());
        resp.setTransactions(transactions);
        resp.setStatus(ResponseStatus.SUCCESS);
        return resp;
    }
}
