package by.agat.system.domain;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int user_id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "DATE_REGISTRATION")
    private Date dateRegistration;

    @Column(name = "ENABLED")
    private byte enabled;

    @Column(name = "DIRECTORY")
    private String directory;

    @Column(name = "BANK")
    private int codeBank;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<DocumentStatus> statuses;


    @Override
    public String toString() {
        return "User{}";
    }
}
