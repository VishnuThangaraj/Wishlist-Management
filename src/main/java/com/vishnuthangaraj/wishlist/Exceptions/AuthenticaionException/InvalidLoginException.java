package com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidLoginException extends RuntimeException{

    /*
        EXCEPTION NAME : AuthenticationFailedException
        DESCRIPTION : This Exception is thrown when an authentication attempt
                      fails due to invalid login credentials. This exception is designed to capture
                      scenarios where the provided login details, such as email and password,
                      do not match any existing user in the system.
        PARAMETERS : 'null'
        HTTP Status Code: 401 Unauthorized
    */
    public InvalidLoginException(){
        super("Invalid login Credentials");
    }

}
