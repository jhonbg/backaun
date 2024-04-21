package com.backaun.aplicacion;

import com.backaun.core.UserRepository;
import com.backaun.core.dominio.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUser {
    @Autowired
    private UserRepository userRepository;

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
        return (User) userRepository.save(user);
    }

    public boolean delete(String userId)
    {
        return getUser(userId).map(user -> {
            userRepository.delete(userId);
            return true;
        }).orElse(false);
    }
}
