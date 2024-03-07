package dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberResponseDto {
    String message;
    ResponseStatus status;
}
