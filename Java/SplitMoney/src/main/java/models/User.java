package models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User extends BaseModel{
    private String name;
    private String alias;
    private String pwd;
    private String phone;
}

