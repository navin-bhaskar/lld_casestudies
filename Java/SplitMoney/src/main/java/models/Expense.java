package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Expense extends BaseModel{
    private int amount;
    private String owedTo;
    private String owedBy;
}
