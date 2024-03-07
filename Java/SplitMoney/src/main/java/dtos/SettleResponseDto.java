package dtos;

import com.splitmoney.splitmoney.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SettleResponseDto {
    private String message;
    private List<Transaction> transactions;
    private ResponseStatus status;
}
