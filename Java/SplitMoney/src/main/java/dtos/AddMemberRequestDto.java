package dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddMemberRequestDto {
    String group;
    String addedBy;
    String member;
}
