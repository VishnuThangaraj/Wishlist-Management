package com.vishnuthangaraj.wishlist.Exceptions.WishlistException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedDeleteException extends RuntimeException {

    /*
        EXCEPTION NAME : UnauthorizedDeleteException
        DESCRIPTION : This Exception is thrown to indicate that an attempt
                      to delete a wishlist item has failed due to a lack of authorization.
                      This exception communicates that the user or calling code does not have
                      the necessary permissions to perform the delete operation, especially
                      when attempting to delete another person's wishlist item.
        PARAMETERS : String (message)
        HTTP Status Code: 403 Forbidden
    */
    public UnauthorizedDeleteException(String message) {
        super(message);
    }
}