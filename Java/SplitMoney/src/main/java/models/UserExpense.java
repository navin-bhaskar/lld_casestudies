package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpense {
    private User user;
    private int amount;
    public UserExpense(User usr) {
        user = usr;
    }
}
