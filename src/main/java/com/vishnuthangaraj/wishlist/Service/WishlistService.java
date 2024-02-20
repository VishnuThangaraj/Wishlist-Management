package com.vishnuthangaraj.wishlist.Service;

import com.vishnuthangaraj.wishlist.DTO.Request.WishlistItemAddRequest;
import com.vishnuthangaraj.wishlist.Exceptions.AuthenticaionException.InvalidTokenException;
import com.vishnuthangaraj.wishlist.Exceptions.WishlistException.EmptyWishlistException;
import com.vishnuthangaraj.wishlist.Exceptions.WishlistException.UnauthorizedDeleteException;
import com.vishnuthangaraj.wishlist.Exceptions.WishlistException.WishlistItemNotFoundException;
import com.vishnuthangaraj.wishlist.Model.AppUser;
import com.vishnuthangaraj.wishlist.Model.WishlistItem;
import com.vishnuthangaraj.wishlist.Repository.AppUserRepository;
import com.vishnuthangaraj.wishlist.Repository.WishlistRepository;
import com.vishnuthangaraj.wishlist.Security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    /*
        SERVICE FUNCTION : getWishlist
        DESCRIPTION : This Service function is responsible for retrieving the
                      wishlist of the currently logged-in user by validating the JWT (JSON Web Token)
                      provided in the request. This function ensures that the user making the request
                      is authenticated to access their own wishlist.
        PARAMETER : 'null'
        RETURN : Returns List of WishListItem on Successful login.
    */
    public List<WishlistItem> getWishlist() throws Exception{
        String currentUserToken = jwtService.getCurrentUserToken();

        if(currentUserToken == null){
            log.warn("The user is attempting to Access Wishlist without Login");
            throw new InvalidTokenException("No Token provided for Authentication");
        }

        String userEmail = jwtService.extractUserName(currentUserToken); // Extract Current User Email
        AppUser currentUser = appUserRepository.getUserByEmail(userEmail); // Get Current User By Email

        if(currentUser.getWishlistItems().isEmpty()){
            throw new EmptyWishlistException("User Does not have any WishlistItem");
        }

        // Return the List of WishlistItem of the CurrentUser
        return currentUser.getWishlistItems();
    }

    /*
        SERVICE FUNCTION : addWishlistItem
        DESCRIPTION : This function is responsible for adding a new wishlist item to the array of
                      wishlist items for the currently logged-in user. This function ensures that
                      the user is authenticated and then processes the request to append the
                      specified item to their wishlist.
        PARAMETER : WishlistItemAddRequest { String (wishlistItemName) }
        RETURN : Returns ResponseEntity on successful WishlistItem Creation, or error response if authentication fails
    */
    public ResponseEntity<String> addWishlistItem(WishlistItemAddRequest wishlistItemAddRequest) throws Exception{
        String currentUserToken = jwtService.getCurrentUserToken();

        if(currentUserToken == null){
            log.warn("The user is attempting to Add Wishlist Item without Login");
            throw new InvalidTokenException("No Token provided for Authentication");
        }

        String userEmail = jwtService.extractUserName(currentUserToken); // Extract Current User Email
        AppUser currentUser = appUserRepository.getUserByEmail(userEmail); // Get Current User By Email

        // Create new Wishlist Item
        var wishlistItem = new WishlistItem();
        wishlistItem.setItemName(wishlistItemAddRequest.getItemName());
        wishlistItem.setDescription(wishlistItemAddRequest.getDescription());
        wishlistItem.setAppUser(currentUser);
        wishlistRepository.save(wishlistItem);
        log.info("New WishlistItem Created");

        // Add wishlistItem to CurrentUser
        currentUser.getWishlistItems().add(wishlistItem);
        appUserRepository.save(currentUser);

        // Return Wishlist Item added message in ResponseEntity
        return new ResponseEntity<>("Wishlist Item Successfully Added to the Current User",HttpStatus.CREATED);
    }

    /*
        SERVICE FUNCTION : deleteWishlistItem
        DESCRIPTION : This function handles the removal of a specific wishlist item from the array
                      of wishlist items for the currently logged-in user. This function ensures
                      that the user is authenticated to delete the item, and then processes
                      the request to remove the specified item from their wishlist.
        PARAMETER : UUID wishlistItemId
        RETURN : Returns ResponseEntity on successful WishlistItem Deletion, or error response if authentication fails
    */
    public ResponseEntity<String> deleteWishlistItem(UUID wishlistItemId) throws Exception{
        String currentUserToken = jwtService.getCurrentUserToken();

        if(currentUserToken == null){
            log.warn("User Trying to Delete Wishlist Item without Login");
            throw new InvalidTokenException("No Token provided for Authentication");
        }

        String userEmail = jwtService.extractUserName(currentUserToken); // Extract Current User Email
        AppUser currentUser = appUserRepository.getUserByEmail(userEmail); // Get Current User By Email

        // Fetch the wishlistItem with ID
        WishlistItem wishlistItem = wishlistRepository.findById(wishlistItemId).orElse(null);

        if(wishlistItem == null) {
            log.warn("The user is attempting to delete a wishlist item with an invalid ID.");
            throw new WishlistItemNotFoundException(wishlistItemId);
        }

        // Check if the WishlistItem belongs to the Current User
        boolean validWishlistItem = currentUser.getWishlistItems().stream()
                .anyMatch(e -> e.getId() == wishlistItemId);
        if(!validWishlistItem){
            log.warn("The user is trying to delete a wishlist item that does not belong to them.");
            throw new UnauthorizedDeleteException("User is not allowed to perform this Operation");
        }

        // Delete WishlistItem from the Current User
        currentUser.getWishlistItems().remove(wishlistItem);
        wishlistRepository.delete(wishlistItem);
        appUserRepository.save(currentUser);

        return new ResponseEntity<>("Wishlist item Deleted", HttpStatus.OK);
    }
}
