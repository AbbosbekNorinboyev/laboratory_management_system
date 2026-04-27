package uz.brb.laboratorymanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.brb.laboratorymanagementsystem.config.CustomUserDetailsService;
import uz.brb.laboratorymanagementsystem.dto.request.LoginRequest;
import uz.brb.laboratorymanagementsystem.dto.request.RegisterRequest;
import uz.brb.laboratorymanagementsystem.dto.request.UpdatePasswordRequest;
import uz.brb.laboratorymanagementsystem.dto.response.ErrorResponse;
import uz.brb.laboratorymanagementsystem.dto.response.Response;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;
import uz.brb.laboratorymanagementsystem.enums.UserRole;
import uz.brb.laboratorymanagementsystem.exception.ResourceNotFoundException;
import uz.brb.laboratorymanagementsystem.repository.AuthUserRepository;
import uz.brb.laboratorymanagementsystem.service.AuthUserService;
import uz.brb.laboratorymanagementsystem.utils.JWTUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static uz.brb.laboratorymanagementsystem.utils.PasswordHasher.hashPassword;
import static uz.brb.laboratorymanagementsystem.utils.PasswordValidator.validatePassword;
import static uz.brb.laboratorymanagementsystem.utils.Utils.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final JWTUtil jwtUtil;
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Response<?> register(RegisterRequest registerRequest) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerRequest.getUsername());
        if (byUsername.isPresent()) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Username already exists")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        Optional<AuthUser> byEmail = authUserRepository.findByEmail(registerRequest.getEmail());
        if (byEmail.isPresent()) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Email already exists")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        AuthUser authUser = new AuthUser();
        authUser.setFullName(registerRequest.getFullName());
        authUser.setUsername(registerRequest.getUsername());
        authUser.setEmail(registerRequest.getEmail());
        authUser.setIsActive(Boolean.TRUE);
        authUser.setPassword(hashPassword(registerRequest.getPassword()));
        authUser.setUserRole(UserRole.USER);
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AuthUser successfully register")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> login(LoginRequest loginRequest) {
        AuthUser authUser = authUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found by username: " + loginRequest.getUsername()));
        if (authUser.getUsername() == null) {
            return Response.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .success(false)
                    .message("Username not found")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        if (!validatePassword(loginRequest.getPassword(), authUser.getPassword())) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Invalid password")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        AuthUser principal = (AuthUser) auth.getPrincipal();
        principal.setLastLoginAt(Instant.now());
        authUserRepository.save(principal);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message(jwtToken)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> changePassword(UpdatePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found by username: " + username));
        if (!passwordEncoder.matches(request.getOldPassword(), authUser.getPassword())) {
            ErrorResponse error = ErrorResponse.builder()
                    .message("Old password is incorrect")
                    .field("oldPassword")
                    .build();
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .errors(List.of(error))
                    .success(false)
                    .message("Old password is incorrect")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        authUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AuthUser successfully change password")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}