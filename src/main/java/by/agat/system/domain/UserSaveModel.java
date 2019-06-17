package by.agat.system.domain;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class UserSaveModel {


    private String username;

    private String password;

    private String surname;

    private String firstname;

    private boolean enabled;

    private int codeBank;

    private int role;
}
