package com.vishnuthangaraj.wishlist.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishnuthangaraj.wishlist.DTO.Request.AuthenticationRequest;
import com.vishnuthangaraj.wishlist.DTO.Request.RegistrationRequest;
import com.vishnuthangaraj.wishlist.Enums.Gender;
import com.vishnuthangaraj.wishlist.Repository.AppUserRepository;
import com.vishnuthangaraj.wishlist.WishlistApplication;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppUserRepository appUserRepository ;


    // Function to check successful User Registration
    @Test
    @Order(1)
    public void successfulUserRegistration () throws Exception {
        // Create a RegistrationRequest object for testing
        RegistrationRequest request = RegistrationRequest.builder()
                .name("Albert")
                .gender(Gender.FEMALE)
                .email("albert@gmail.com")
                .password("albert%123").build();

        // Convert RegistrationRequest to JSON
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        appUserRepository.deleteById(appUserRepository.findByEmail(request.getEmail()).get().getId());
    }

    // Function to check Duplicate User Registration
    @Test
    @Order(2)
    public void failureUserRegistration () throws Exception {
        // Create a RegistrationRequest object for testing
        RegistrationRequest request = RegistrationRequest.builder()
                .name("John")
                .gender(Gender.FEMALE)
                .email("john@gmail.com")
                .password("john%123").build();

        // Convert RegistrationRequest to JSON
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Perform the POST request and validate the response // Registering with same Email
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isConflict());

        appUserRepository.deleteById(appUserRepository.findByEmail(request.getEmail()).get().getId());
    }


    // Function to check successful User Login
    @Test
    @Order(3)
    public void successfulLogin() throws Exception{
        // Create a RegistrationRequest object for testing
        RegistrationRequest request = RegistrationRequest.builder()
                .name("Andrew")
                .gender(Gender.FEMALE)
                .email("andrew@gmail.com")
                .password("andrew%123").build();

        // Convert RegistrationRequest to JSON
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request and validate the response for registration
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Create Authentication request to log in
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                        .email(request.getEmail())
                        .password(request.getPassword()).build();

        // Convert RegistrationRequest to JSON
        String loginJson = objectMapper.writeValueAsString(authenticationRequest);

        // Perform the POST request and validate the response for login
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        appUserRepository.deleteById(appUserRepository.findByEmail(request.getEmail()).get().getId());
    }

    // Function to check the User Login with Invalid credentials
    @Test
    @Order(4)
    public void badCredentialsLogin() throws Exception{
        // Create Authentication request to log in
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("invalid@email.com")
                .password("wrongPassword").build();

        // Convert RegistrationRequest to JSON
        String loginJson = objectMapper.writeValueAsString(authenticationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
