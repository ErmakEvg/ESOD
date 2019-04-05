package by.agat.system.services;

import by.agat.system.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUser();

    User findByUsername(String username);

    User save(User user);
}
