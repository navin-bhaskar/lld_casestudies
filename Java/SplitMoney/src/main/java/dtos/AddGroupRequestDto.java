package dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddGroupRequestDto {
    private String groupName;
    private String admin;
    private String description;
}
