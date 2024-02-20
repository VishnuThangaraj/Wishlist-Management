package com.vishnuthangaraj.wishlist.Exceptions.WishlistException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyWishlistException extends RuntimeException {

    /*
        EXCEPTION NAME : EmptyWishlistException
        DESCRIPTION : This Exception is thrown to indicate that an attempt to
                      retrieve the wishlist of the currently logged-in user has resulted in
                      an empty wishlist.
        PARAMETERS : String (message)
        HTTP Status Code: 404 Not Found
    */
    public EmptyWishlistException(String message) {
        super(message);
    }
}