package com.vishnuthangaraj.wishlist.Exceptions.WishlistException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishlistItemNotFoundException extends RuntimeException {

    /*
        EXCEPTION NAME : WishlistItemNotFoundException
        DESCRIPTION : This Exception is thrown to indicate that an attempt to retrieve or manipulate
                      a specific wishlist item has failed because the requested item could not be found.
                      This exception is designed to handle scenarios where a specific wishlist item is
                      expected to exist, but the system cannot locate it based on the provided identifier.
        PARAMETERS : UUID wishlistItemId
        HTTP Status Code: 404 Not Found
    */
    public WishlistItemNotFoundException(UUID wishlistItemId) {
        super("WishlistItem with Id : "+wishlistItemId+", Does not Exists.");
    }
}
