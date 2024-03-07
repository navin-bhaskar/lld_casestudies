package dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SettleGroupRequestDto {
    private String requestedBy;
    private String group;
}
