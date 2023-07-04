package com.otto.bookinghotelbe.service;

import com.otto.bookinghotelbe.auth.AuthenticateResponse;
import com.otto.bookinghotelbe.auth.AuthenticationRequest;
import com.otto.bookinghotelbe.auth.RegisterRequest;
import com.otto.bookinghotelbe.model.Role;
import com.otto.bookinghotelbe.model.User;
import com.otto.bookinghotelbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phonenumber(request.getPhonenumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticateResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }
}
