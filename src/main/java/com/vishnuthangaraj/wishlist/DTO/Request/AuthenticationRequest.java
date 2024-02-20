package com.vishnuthangaraj.wishlist.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /*
        CLASS NAME : AuthenticationRequest
        DESCRIPTION : This class serves as a Data Transfer Object (DTO) designed to encapsulate
                      user authentication credentials during the login or authentication process.
                      This class acts as a lightweight container for transporting essential
                      authentication information, specifically the user's email and password,
                      between the client and the server.
        ATTRIBUTES : String (email), String (password)
    */

    private String email;
    private String password;

}
