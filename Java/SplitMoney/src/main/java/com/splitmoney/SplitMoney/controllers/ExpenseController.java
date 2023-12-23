package com.splitmoney.splitmoney.controllers;

import com.splitmoney.splitmoney.models.User;
import com.splitmoney.splitmoney.models.UserExpense;
import com.splitmoney.splitmoney.models.UserExpenseType;
import com.splitmoney.splitmoney.services.ExpenseService;
import com.splitmoney.splitmoney.services.UserService;
import dtos.AddExpenseRequestDto;
import dtos.AddExpenseResponseDto;
import dtos.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseController {
    private ExpenseService expenseService;
    private UserService userService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
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
}
