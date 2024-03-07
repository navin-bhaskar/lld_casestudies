package com.splitmoney.splitmoney.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Expense extends BaseModel{
    private String description;
    private int amount;
    @ManyToOne
    private User createdBy;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType type;
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER)
    List<UserExpense> userExpenses;
}
