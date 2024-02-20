package com.vishnuthangaraj.wishlist.DTO.Request;

import com.vishnuthangaraj.wishlist.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    /*
        CLASS NAME : RegistrationRequest
        DESCRIPTION : This class functions as a Data Transfer Object (DTO) designed to encapsulate
                      user registration details, providing a structured format for transmitting
                      information from clients to servers during the registration process.
                      This class captures key user attributes such as name, gender, email, and password,
                      facilitating the creation of new user accounts.
        ATTRIBUTES : String (name), Enum<Gender> (gender), String (email), String (password)
    */

    private String name;
    private Gender gender;
    private String email;
    private String password;

}
