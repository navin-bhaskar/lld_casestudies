package models;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Group extends BaseModel{
    private String name;
    private String alias;
    @OneToOne
    private User admin;
    @OneToMany
    private List<User> participants;
    @OneToMany
    private List<UserExpense> expenses;
}
