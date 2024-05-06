package com.backaun.core;

import com.backaun.core.interfaces.UserCrudRepository;
import com.backaun.core.dominio.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private UserCrudRepository userCrudRepository;
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserEmail(String userEmail)
    {
        return userCrudRepository.findByUserEmail(userEmail);
    }

    public Optional<User> getUser(String userId)
    {
        return userCrudRepository.findById(userId);
    }

    public User save(User user)
    {
        return (User) userCrudRepository.save(user);
    }

    public void delete(String userId)
    {
        userCrudRepository.deleteById(userId);
    }

    public User updateUser(User user)
    {
        Optional<User> existingUserOptinal = getUser(user.getUserId());

        if(existingUserOptinal.isPresent())
        {
            User exitingUser = existingUserOptinal.get();
            exitingUser.setUserName(user.getUserName());
            exitingUser.setUserLastname(user.getUserLastname());
            exitingUser.setUserEmail(user.getUserEmail());
            exitingUser.setUserPhoneNumber(user.getUserPhoneNumber());
            return userCrudRepository.save(exitingUser);
        }
        return null;
    }

    public User updatePassword(User user)
    {
        return userCrudRepository.save(user);
    }
}
