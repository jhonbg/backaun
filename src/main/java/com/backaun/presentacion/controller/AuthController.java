package com.backaun.presentacion.controller;

import com.backaun.aplicacion.AuthService;
import com.backaun.aplicacion.ServiceUser;
import com.backaun.core.dominio.AuthRespose;
import com.backaun.core.dominio.LoginRequest;
import com.backaun.core.dominio.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    private final ServiceUser serviceUser;

    @PostMapping(value = "login")
    public ResponseEntity<AuthRespose> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthRespose> register(@RequestBody RegisterRequest request)
    {
        if (serviceUser.getUserEmail(request.getUserEmail()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else if (serviceUser.getUser(request.getUserId()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        else
        {
            return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
        }
    }
}
