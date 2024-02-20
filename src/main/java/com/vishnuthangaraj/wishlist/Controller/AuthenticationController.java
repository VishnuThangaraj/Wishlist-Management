package com.vishnuthangaraj.wishlist.Controller;

import com.vishnuthangaraj.wishlist.DTO.Request.AuthenticationRequest;
import com.vishnuthangaraj.wishlist.DTO.Request.RegistrationRequest;
import com.vishnuthangaraj.wishlist.DTO.Response.AuthenticationResponse;
import com.vishnuthangaraj.wishlist.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    /*
        URL : http://localhost:8081/api/auth/register
        CONTROLLER FUNCTION : userRegistration
        DESCRIPTION : Handles HTTP POST requests for user registration.
                      Calls the Authentication service to process the registration request.
        PARAMETER : RegistrationRequest {name, gender, email, password} | JSON Object
        RETURN : Returns JWT Token on successful Registration, or error if registration fails.
        AUTHENTICATION : 'No-Token Required for registration'
    */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> userRegistration(
            @RequestBody RegistrationRequest request
    ){
        return ResponseEntity.ok(authService.userRegistration(request));
    }

    /*
        URL : http://localhost:8081/api/auth/authenticate
        CONTROLLER FUNCTION : userAuthentication
        DESCRIPTION : Handles User login process by validating the provided credentials.
                      This function necessitates a JSON Object containing of email and password
                      through request Body.
                      If the credentials are valid, generates a JWT token and sends it in the response.
                      If the credentials are invalid, returns a response with an appropriate HTTP status.
        PARAMETER : AppUserAuthenticationRequest {(String) email , (String) password} | JSON Object
        RETURN : Returns JWT Token on successful Login, or error response if authentication fails
        AUTHENTICATION : 'No-Token Required for Login'
    */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> userAuthentication(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticateAndLogin(request));
    }
}
