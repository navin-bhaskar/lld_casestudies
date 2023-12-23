package com.splitmoney.splitmoney.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "user_group")
@EntityListeners(AuditingEntityListener.class)
public class Group extends BaseModel{
    private String name;
    @ManyToOne
    private User createdBy;
    @ManyToMany
    private List<User> participants;
    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;

    public String getGroupAlias() {
        return "G" + getId();
    }

}
