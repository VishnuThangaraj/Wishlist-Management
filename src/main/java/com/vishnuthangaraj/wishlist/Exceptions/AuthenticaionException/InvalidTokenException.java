package com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends AuthenticationException {

    /*
        EXCEPTION NAME : InvalidTokenException
        DESCRIPTION : This Exception is thrown to indicate an authentication failure
                      due to an invalid or missing authentication token.
                      This exception is commonly used in authentication processes, such as
                      when validating tokens for secured operations, and signifies that the provided
                      token is either not recognized, expired, or otherwise invalid.
        PARAMETERS : String (message)
        HTTP Status Code: 401 Unauthorized
    */
    public InvalidTokenException(String message){
        super(message);
    }
}
