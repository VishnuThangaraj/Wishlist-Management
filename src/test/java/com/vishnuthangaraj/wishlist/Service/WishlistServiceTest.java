package com.vishnuthangaraj.wishlist.Service;

import com.vishnuthangaraj.wishlist.DTO.Request.WishlistItemAddRequest;
import com.vishnuthangaraj.wishlist.Exceptions.WishlistException.EmptyWishlistException;
import com.vishnuthangaraj.wishlist.Exceptions.WishlistException.UnauthorizedDeleteException;
import com.vishnuthangaraj.wishlist.Model.AppUser;
import com.vishnuthangaraj.wishlist.Model.WishlistItem;
import com.vishnuthangaraj.wishlist.Repository.AppUserRepository;
import com.vishnuthangaraj.wishlist.Repository.WishlistRepository;
import com.vishnuthangaraj.wishlist.Security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWishlist() throws Exception {
        // Mock data
        String currentUserToken = "mockToken";
        String userEmail = "user@example.com";
        AppUser currentUser = new AppUser();
        currentUser.setWishlistItems(new ArrayList<>());

        // Mock behavior
        when(jwtService.getCurrentUserToken()).thenReturn(currentUserToken);
        when(jwtService.extractUserName(currentUserToken)).thenReturn(userEmail);
        when(appUserRepository.getUserByEmail(userEmail)).thenReturn(currentUser);

        // Perform the test
        assertThrows(EmptyWishlistException.class, () -> wishlistService.getWishlist());

        // Verify behavior
        verify(jwtService, times(1)).getCurrentUserToken();
        verify(jwtService, times(1)).extractUserName(currentUserToken);
        verify(appUserRepository, times(1)).getUserByEmail(userEmail);
    }

    @Test
    void testAddWishlistItem() throws Exception {
        // Mock data
        String currentUserToken = "mockToken";
        String userEmail = "user@example.com";
        AppUser currentUser = new AppUser();
        currentUser.setWishlistItems(new ArrayList<>());
        WishlistItemAddRequest request = new WishlistItemAddRequest();
        request.setItemName("New Item");

        // Mock behavior
        when(jwtService.getCurrentUserToken()).thenReturn(currentUserToken);
        when(jwtService.extractUserName(currentUserToken)).thenReturn(userEmail);
        when(appUserRepository.getUserByEmail(userEmail)).thenReturn(currentUser);

        // Perform the test
        ResponseEntity<String> response = wishlistService.addWishlistItem(request);

        // Verify behavior
        verify(jwtService, times(1)).getCurrentUserToken();
        verify(jwtService, times(1)).extractUserName(currentUserToken);
        verify(appUserRepository, times(1)).getUserByEmail(userEmail);
        verify(wishlistRepository, times(1)).save(any(WishlistItem.class));
        verify(appUserRepository, times(1)).save(currentUser);

        // Perform assertions on the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Wishlist Item Successfully Added to the Current User", response.getBody());
    }

    @Test
    void testDeleteWishlistItemBelongsToUser() throws Exception {
        // Mock data
        String currentUserToken = "mockToken";
        String userEmail = "user@example.com";
        AppUser currentUser = new AppUser();
        currentUser.setWishlistItems(new ArrayList<>());
        UUID wishlistItemId = UUID.randomUUID();
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setId(wishlistItemId);
        currentUser.getWishlistItems().add(wishlistItem);

        // Mock behavior
        when(jwtService.getCurrentUserToken()).thenReturn(currentUserToken);
        when(jwtService.extractUserName(currentUserToken)).thenReturn(userEmail);
        when(appUserRepository.getUserByEmail(userEmail)).thenReturn(currentUser);
        when(wishlistRepository.findById(wishlistItemId)).thenReturn(java.util.Optional.of(wishlistItem));

        // Perform the test
        ResponseEntity<String> response = wishlistService.deleteWishlistItem(wishlistItemId);

        // Verify behavior
        verify(jwtService, times(1)).getCurrentUserToken();
        verify(jwtService, times(1)).extractUserName(currentUserToken);
        verify(appUserRepository, times(1)).getUserByEmail(userEmail);
        verify(wishlistRepository, times(1)).findById(wishlistItemId);
        verify(wishlistRepository, times(1)).delete(wishlistItem);
        verify(appUserRepository, times(1)).save(currentUser);

        // Perform assertions on the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Wishlist item Deleted", response.getBody());
    }

    @Test
    void testDeleteWishlistItemNotBelongsToUser() throws Exception {
        // Mock data
        String currentUserToken = "mockToken";
        String userEmail = "user@example.com";
        AppUser currentUser = new AppUser();
        currentUser.setWishlistItems(new ArrayList<>());
        UUID wishlistItemId = UUID.randomUUID();
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setId(wishlistItemId);

        // Mock behavior
        when(jwtService.getCurrentUserToken()).thenReturn(currentUserToken);
        when(jwtService.extractUserName(currentUserToken)).thenReturn(userEmail);
        when(appUserRepository.getUserByEmail(userEmail)).thenReturn(currentUser);
        when(wishlistRepository.findById(wishlistItemId)).thenReturn(java.util.Optional.of(wishlistItem));

        // Perform the test
        assertThrows(UnauthorizedDeleteException.class, () -> wishlistService.deleteWishlistItem(wishlistItemId));

        // Verify behavior
        verify(jwtService, times(1)).getCurrentUserToken();
        verify(jwtService, times(1)).extractUserName(currentUserToken);
        verify(appUserRepository, times(1)).getUserByEmail(userEmail);
        verify(wishlistRepository, times(1)).findById(wishlistItemId);
        verify(wishlistRepository, never()).delete(wishlistItem); // Ensure delete is never called
        verify(appUserRepository, never()).save(currentUser); // Ensure save is never called
    }
}
