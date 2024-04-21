package com.backaun.core;

import com.backaun.core.interfaces.UserCrudRepository;
import com.backaun.core.dominio.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private UserCrudRepository userCrudRepository;

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

}
