package com.vishnuthangaraj.wishlist.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItemAddRequest {

    /*
        CLASS NAME : WishlistItemAddRequest
        DESCRIPTION : This class serves as a specialized Data Transfer Object (DTO) designed for
                      collecting and transmitting information related to the addition of new items
                      to a user's wishlist. This class encapsulates a minimal set of attributes,
                      specifically focusing on the name of the wishlist item,
                      making it suitable for streamlined communication between client applications
                      and server endpoints during wishlist item creation.
        ATTRIBUTES : String (itemName), String (description)
    */

    private String itemName;
    private String description;

}
