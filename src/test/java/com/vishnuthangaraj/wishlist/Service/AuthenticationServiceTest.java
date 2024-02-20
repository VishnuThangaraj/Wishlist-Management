package com.vishnuthangaraj.wishlist.Service;

import com.vishnuthangaraj.wishlist.DTO.Request.AuthenticationRequest;
import com.vishnuthangaraj.wishlist.DTO.Request.RegistrationRequest;
import com.vishnuthangaraj.wishlist.DTO.Response.AuthenticationResponse;
import com.vishnuthangaraj.wishlist.Enums.Gender;
import com.vishnuthangaraj.wishlist.Enums.Role;
import com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException.DuplicateEmailException;
import com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException.InvalidLoginException;
import com.vishnuthangaraj.wishlist.Model.AppUser;
import com.vishnuthangaraj.wishlist.Repository.AppUserRepository;
import com.vishnuthangaraj.wishlist.Security.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testUserRegistration() {
        // Mock data
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .name("John")
                .email("john@gmail.com")
                .gender(Gender.MALE)
                .password("john%123").build();

        // Mock repository response
        when(appUserRepository.findByEmail(registrationRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn("encodedPassword");

        // Mock saved user
        AppUser savedUser = AppUser.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password("encodedPassword")
                .role(Role.USER)
                .gender(registrationRequest.getGender())
                .build();

        when(appUserRepository.save(any(AppUser.class))).thenReturn(savedUser);

        // Mock JWT token
        when(jwtService.generateToken(savedUser)).thenReturn("mockJwtToken");

        // Perform the registration
        AuthenticationResponse authenticationResponse = authenticationService.userRegistration(registrationRequest);

        // Assertions
        assertNotNull(authenticationResponse);
        assertEquals("mockJwtToken", authenticationResponse.getToken());
    }

    @Test
    void testUserRegistrationWithDuplicateEmail() {
        // Mock data
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .name("John")
                .email("john@gmail.com")
                .gender(Gender.MALE)
                .password("john%123").build();

        // Mock repository response indicating duplicate email
        when(appUserRepository.findByEmail(registrationRequest.getEmail())).thenReturn(Optional.of(new AppUser()));

        // Perform the registration and expect DuplicateEmailException
        assertThrows(DuplicateEmailException.class, () -> authenticationService.userRegistration(registrationRequest));
    }

    @Test
    void testAuthenticateAndLogin() {
        // Mock data
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@gmail.com", "john%123");

        // Mock repository response
        AppUser mockUser = AppUser.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .gender(Gender.MALE)
                .build();

        when(appUserRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(mockUser));

        // Mock authentication manager response
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        // Mock JWT token
        when(jwtService.generateToken(mockUser)).thenReturn("mockJwtToken");

        // Perform authentication and login
        AuthenticationResponse authenticationResponse = authenticationService.authenticateAndLogin(authenticationRequest);

        // Assertions
        assertNotNull(authenticationResponse);
        assertEquals("mockJwtToken", authenticationResponse.getToken());
    }

    @Test
    void testAuthenticateAndLoginWithInvalidCredentials() {
        // Mock data
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.com", "wrongPassword");

        // Mock repository response
        when(appUserRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.empty());

        // Mock authentication manager response
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new InvalidLoginException());

        // Perform authentication and login and expect InvalidLoginException
        assertThrows(InvalidLoginException.class, () -> authenticationService.authenticateAndLogin(authenticationRequest));
    }
}

