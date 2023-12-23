package dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupResponseDto {
    private String alias;
    private ResponseStatus status;
    private String error;
}
