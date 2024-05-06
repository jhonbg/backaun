package com.backaun.aplicacion;

import com.backaun.core.UserRepository;
import com.backaun.core.dominio.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceUser {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> getUserEmail(String userEmail)
    {
        return userRepository.getUserEmail(userEmail);
    }
    public Optional<User> getUser(String userId)
    {
        return userRepository.getUser(userId);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public boolean delete(String userId)
    {
        return getUser(userId).map(user -> {
            userRepository.delete(userId);
            return true;
        }).orElse(false);
    }

    public Boolean changePassword(String email, String passwordCurrent, String passwordNew)
    {
        Optional<User> user = getUserEmail(email);
        if(user.isPresent())
        {
            User userEdit = user.get();
            if(userEdit.getPassword().equals(passwordCurrent))
            {
                userEdit.setUserPassword(passwordEncoder.encode(passwordNew));
                userRepository.save(userEdit);
                return true;
            }
        }
        return false;
    }

    public User updateUser(User user)
    {
        return userRepository.updateUser(user);
    }

    public User updatePassword(User user)
    {
        return userRepository.updatePassword(user);
    }
}
