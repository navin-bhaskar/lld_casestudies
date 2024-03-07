package dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequestDto {
    private String userName;
    private String userPhoneNum;
    private String password;
}
