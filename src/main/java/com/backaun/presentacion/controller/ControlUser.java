package com.backaun.presentacion.controller;

import com.backaun.aplicacion.ServiceUser;
import com.backaun.core.dominio.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ControlUser {
    @Autowired
    private ServiceUser serviceUser;

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
}
