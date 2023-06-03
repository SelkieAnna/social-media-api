package com.selkieanna.socialmediaapi.authorization.controller;

import com.selkieanna.socialmediaapi.authorization.request.SignInRequest;
import com.selkieanna.socialmediaapi.authorization.request.SignUpRequest;
import com.selkieanna.socialmediaapi.authorization.response.JwtResponse;
import com.selkieanna.socialmediaapi.util.response.MessageResponse;
import com.selkieanna.socialmediaapi.authorization.model.Role;
import com.selkieanna.socialmediaapi.authorization.model.RoleEnum;
import com.selkieanna.socialmediaapi.authorization.model.User;
import com.selkieanna.socialmediaapi.authorization.repository.RoleRepository;
import com.selkieanna.socialmediaapi.authorization.repository.UserRepository;
import com.selkieanna.socialmediaapi.authorization.security.jwt.JwtUtils;
import com.selkieanna.socialmediaapi.authorization.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        // message if username taken
        if (userRepository.existsByName(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: This username is unavailable"));
        }
        // message if email taken
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User with such email already exists"));
        }
        // create user
        User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // new jwt token
        String jwtToken = jwtUtils.generateToken(authentication);
        // getting user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();
        return ResponseEntity.ok(new JwtResponse(
                jwtToken, Long.valueOf(userDetails.getId()),
                userDetails.getUsername(), userDetails.getEmail()));
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    @GetMapping("/authorized")
    public ResponseEntity<?> authorized() {
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
