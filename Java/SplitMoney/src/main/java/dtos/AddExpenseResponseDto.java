package dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddExpenseResponseDto {
    private String message;
    private ResponseStatus status;
}
