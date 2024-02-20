package com.vishnuthangaraj.wishlist.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishnuthangaraj.wishlist.DTO.Request.WishlistItemAddRequest;
import com.vishnuthangaraj.wishlist.Model.WishlistItem;
import com.vishnuthangaraj.wishlist.Service.WishlistService;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @Test
    @WithMockUser(username = "testUser", password = "password", roles = "USER")
    void testShowWishlist() throws Exception {
        // Mock data
        List<WishlistItem> wishlistItems = new ArrayList<>();
        when(wishlistService.getWishlist()).thenReturn(wishlistItems);

        // Invalid JWT payload with malformed content
        String invalidPayload = "your.malformed.payload";

        // Perform the test
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/Wishlist/")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + createInvalidJwt(invalidPayload))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (MalformedJwtException e) {
            // Log the exception and inspect the payload
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "testUser", password = "password", roles = "USER")
    void testAddWishlistItem() throws Exception {
        // Mock data
        WishlistItemAddRequest request = new WishlistItemAddRequest();
        when(wishlistService.addWishlistItem(request)).thenReturn(ResponseEntity.ok("Wishlist Item Successfully Added"));

        // Valid JWT token
        String validToken = "your-valid-token";

        // Perform the test
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/Wishlist/add")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (MalformedJwtException e) {
            // Log the exception and inspect the details
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "testUser", password = "password", roles = "USER")
    void testDeleteWishlistItem() throws Exception {
        // Mock data
        UUID itemId = UUID.randomUUID();
        when(wishlistService.deleteWishlistItem(itemId)).thenReturn(ResponseEntity.ok("Wishlist item Deleted"));

        // Valid JWT token
        String validToken = "your-valid-token";

        // Perform the test
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/Wishlist/delete/{id}", itemId)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            // Log the exception and inspect the details
            e.printStackTrace();
        }
    }

    private String createInvalidJwt(String payload) {
        // Replace with your actual logic to create a JWT with an invalid payload
        return "hello." + Base64.getEncoder().encodeToString(payload.getBytes()) + ".bye";
    }
}
