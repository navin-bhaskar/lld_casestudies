package com.splitmoney.splitmoney.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Optional;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseModel{
    private String name;
    private String pwd;
    private String phone;

    public static Optional<Long> getIdFromAlias(String alias) {
        if (alias.length() <= 1 || (alias.charAt(0) != 'u' && alias.charAt(0) != 'U')) {
            return Optional.empty();
        }
        Long id;
        String strId = alias.substring(1);
        try {
            id = Long.parseLong(strId);
            return Optional.of(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    public String getAlias() {
        return "U" + this.getId();
    }

}

