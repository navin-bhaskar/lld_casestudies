package com.splitmoney.splitmoney.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Optional;

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
    public void addUser(User usr) {
        this.participants.add(usr);
    }

    public static Optional<Long> getIdFromAlias(String alias) {
        if (alias.length() <= 1 || (alias.charAt(0) != 'g' && alias.charAt(0) != 'G')) {
            return Optional.empty();
        }
        Long id;
        String strId = alias.substring(1);
        try {
            id = Long.parseLong(strId);
            return Optional.of(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
