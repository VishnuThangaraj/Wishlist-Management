package com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException{

    /*
        EXCEPTION NAME : DuplicateEmailException
        DESCRIPTION : This exception is thrown when attempting to register or create a new user
                      with an email that already exists in the system. It signifies a conflict with the
                      current state of the application,
                      indicating that a user with the specified email address is already registered.
        PARAMETERS : String (UserEmail)
        HTTP Status Code: 409 Conflict
    */

    public DuplicateEmailException(String email){
        super("User with email : "+email+" Already Exists...");
    }

}
