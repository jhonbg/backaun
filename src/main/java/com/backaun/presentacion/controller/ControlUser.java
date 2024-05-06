package com.backaun.presentacion.controller;

import com.backaun.aplicacion.ServiceUser;
import com.backaun.core.dominio.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ControlUser {
    @Autowired
    private ServiceUser serviceUser;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") String userId)
    {
        return serviceUser.getUser(userId);
    }

    @PostMapping("/save")
    public  User save(@RequestBody User user)
    {
        return serviceUser.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") String userId)
    {
        return serviceUser.delete(userId);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {
        if(serviceUser.getUser(user.getUserId()).isPresent())
        {
            return new ResponseEntity<>(serviceUser.updateUser(user),HttpStatus.ACCEPTED);
        }
        else if (serviceUser.getUserEmail(user.getUserEmail()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<User> updatePassword(@RequestBody Map<String, String> passwords)
    {
        String userId = passwords.get("userId");
        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        Optional<User> existingUserOptinal = serviceUser.getUser(userId);
        if(existingUserOptinal.isPresent())
        {
            User existingUser = existingUserOptinal.get();

            if(passwordEncoder.matches(currentPassword, existingUser.getPassword()))
            {
                String encryptedPassword = passwordEncoder.encode(newPassword);
                existingUser.setUserPassword(encryptedPassword);
                return new ResponseEntity<>(serviceUser.updatePassword(existingUser),HttpStatus.ACCEPTED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
