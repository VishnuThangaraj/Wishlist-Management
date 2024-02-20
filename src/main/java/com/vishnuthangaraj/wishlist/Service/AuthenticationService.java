package com.vishnuthangaraj.wishlist.Service;

import com.vishnuthangaraj.wishlist.DTO.Request.AuthenticationRequest;
import com.vishnuthangaraj.wishlist.DTO.Request.RegistrationRequest;
import com.vishnuthangaraj.wishlist.DTO.Response.AuthenticationResponse;
import com.vishnuthangaraj.wishlist.Enums.Role;
import com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException.DuplicateEmailException;
import com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException.InvalidLoginException;
import com.vishnuthangaraj.wishlist.Model.AppUser;
import com.vishnuthangaraj.wishlist.Repository.AppUserRepository;
import com.vishnuthangaraj.wishlist.Security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private  final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    /*
        SERVICE FUNCTION : userRegistration
        DESCRIPTION : This function is responsible for checking the existence of an email in the system.
                      If the email is not already associated with an existing user account,
                      it proceeds to create a new user account with the provided details.
        PARAMETER : RegistrationRequest {name, gender, email, password}
        RETURN : Returns JwtToken if Registration is Successful
    */
    public AuthenticationResponse userRegistration(RegistrationRequest request) {

        // Check if the Email Already Exists
        if(appUserRepository.findByEmail(request.getEmail()).isPresent()){
            log.warn("User Provided Duplicate Email for Registration");
            throw new DuplicateEmailException(request.getEmail());
        }

        var enrolledUser = AppUser.builder() // Register New User
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .gender(request.getGender())
                .build();

        appUserRepository.save(enrolledUser);

        log.info("New User Registration Completed");

        // Generate Token and return Authentication Response
        var jwtToken = jwtService.generateToken(enrolledUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /*
        SERVICE FUNCTION : authenticateAndLogin
        DESCRIPTION : This service function is designed to authenticate a user by verifying their
                      credentials against the stored information in the system.
                      Upon successful authentication, it performs the login operation, generating
                      and returning an authentication token for the user's session.
        PARAMETER : AuthenticationRequest {(String) email , (String) password}
        RETURN : Returns JwtToken if Registration is Successful, else throws InvalidLoginException
    */
    public AuthenticationResponse authenticateAndLogin(AuthenticationRequest request){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Validate the Credentials
        var user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidLoginException::new);

        log.info("User Login Successful");

        // Generate Token and return Authentication Response
        var jwtToken = jwtService.generateToken(user);
        log.info("Token Generated Successfully");

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
