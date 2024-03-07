package dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserResponseDto {
    private String userAlias;
    private String error;
    private ResponseStatus status;
}
