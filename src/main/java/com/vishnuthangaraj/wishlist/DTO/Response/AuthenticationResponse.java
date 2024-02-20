package com.vishnuthangaraj.wishlist.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

     /*
        CLASS NAME : AuthenticationResponse
        DESCRIPTION : This class serves as a Data Transfer Object (DTO) tailored for encapsulating
                      the outcome of a successful authentication process. Specifically, this class
                      carries information about the authentication result, typically a secure token,
                      allowing for communication between the server and client applications.
        ATTRIBUTES : String (token) {JwtToken}
    */

    private String token;
}

