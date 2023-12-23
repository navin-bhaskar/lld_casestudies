package dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class AddExpenseRequestDto {
    private List<String> usersWhoPaid;
    private List<String> usersWhoHadToPay;
    private List <Integer> paidAmount;
    private List <Integer> owedAmount;
    private String createdBy;
    private String description;
    private int totalAmount;

//    public void addPaidUserAmt(String user, int amount) {
//        usersWhoPaid.add(user);
//        paidAmount.add(amount);
//    }
//
//    public void addOwedUserAmt(String user, int amount) {
//        usersWhoHadToPay.add(user);
//        owedAmount.add(amount);
//    }
//
//    public Pair<List<String>, List <Integer>> getUsersWhoPaidAmtPair() {
//        return  Pair.of(this.usersWhoPaid, this.paidAmount);
//    }
//
//    public Pair<List<String>, List <Integer>> getUsersWhoHadToPayAmtPair() {
//        return  Pair.of(this.usersWhoHadToPay, this.owedAmount);
//    }
//
//    public void addCreatedBy(String user) {
//        createdBy = user;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
}
