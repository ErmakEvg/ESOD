package by.agat.system.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class UserDTO {

    private int id;
    private String username;
    private String surname;
    private String firstname;
    private Date dateRegistration;
    private Role role;
    private int codeBank;
    private byte enabled;

}
