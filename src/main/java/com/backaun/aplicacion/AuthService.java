package com.backaun.aplicacion;

import com.backaun.core.UserRepository;
import com.backaun.core.dominio.AuthRespose;
import com.backaun.core.dominio.LoginRequest;
import com.backaun.core.dominio.RegisterRequest;
import com.backaun.core.dominio.User;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.webresources.ClasspathURLStreamHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthRespose login(LoginRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getUserPassword()));
        User user= userRepository.getUserEmail(request.getUserEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthRespose.builder()
                .token(token)
                .build();
    }

    public AuthRespose register(RegisterRequest request)
    {
        User user = User.builder()
                .userId(request.getUserId())
                .userIdTipe(request.getUserIdTipe())
                .userName(request.getUserName())
                .userLastname(request.getUserLastname())
                .userPhoneNumber(request.getUserPhoneNumber())
                .userEmail(request.getUserEmail())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .roleId(request.getRoleId())
                .build();

        userRepository.save(user);

        return AuthRespose.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
