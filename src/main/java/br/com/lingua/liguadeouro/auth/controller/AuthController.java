package br.com.lingua.liguadeouro.auth.controller;


import br.com.lingua.liguadeouro.auth.JwtTokenProvider;
import br.com.lingua.liguadeouro.auth.model.Role;
import br.com.lingua.liguadeouro.auth.model.User;
import br.com.lingua.liguadeouro.auth.payload.ApiResponse;
import br.com.lingua.liguadeouro.auth.payload.JwtAuthenticationResponse;
import br.com.lingua.liguadeouro.auth.payload.LoginRequest;
import br.com.lingua.liguadeouro.auth.payload.SignUpRequest;
import br.com.lingua.liguadeouro.auth.repository.UserRepository;
import br.com.lingua.liguadeouro.auth.service.RoleService;
import br.com.lingua.liguadeouro.auth.service.UserService;
import br.com.lingua.liguadeouro.enums.RoleNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired(required=true)
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(this.userRepository.findByName(signUpRequest.getUsername()).isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Usuário ou senha já existente!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(this.userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Usuário ou senha já existente"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole =
                this.roleService.findOne(Example.of(Role.builder().name(RoleNameEnum.ROLE_USER).build()))
                .orElseThrow(() -> new RuntimeException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        Optional<User> result = this.userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.get().getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}