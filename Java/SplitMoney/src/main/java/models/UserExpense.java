package models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpense {
    @OneToOne
    private User user;
    @OneToOne
    private Expense expense;
    @Enumerated(EnumType.ORDINAL)
    UserExpense usrExpType;
}
