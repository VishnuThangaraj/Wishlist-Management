package com.vishnuthangaraj.wishlist.Controller;

import com.vishnuthangaraj.wishlist.DTO.Request.WishlistItemAddRequest;
import com.vishnuthangaraj.wishlist.Model.WishlistItem;
import com.vishnuthangaraj.wishlist.Service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/Wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    /*
        URL : http://localhost:8081/api/Wishlist/
        CONTROLLER FUNCTION : showWishlist
        DESCRIPTION : Handles HTTP GET request for providing the Wishlist Item for the loggedIn
                      AppUser with JwtToken
        PARAMETER : 'null'
        RETURN : Returns List of Wishlist Items on successful Login, or error response if authentication fails
        AUTHENTICATION : 'Token Required'
    */
    @GetMapping("/")
    public ResponseEntity<List<WishlistItem>> showWishlist() throws Exception {
        return ResponseEntity.ok(wishlistService.getWishlist());
    }

    /*
        URL : http://localhost:8081/api/Wishlist/add
        CONTROLLER FUNCTION : addWishlistItem
        DESCRIPTION : This controller function is responsible for handling requests to add a new
                      wishlist item for the currently logged-in user. This processes the request
                      to add the specified item to their wishlist.
        PARAMETER : WishlistItemAddRequest { String (wishlistItemName) } | JSON Object
        RETURN : Returns ResponseEntity on successful WishlistItem Creation, or error response if authentication fails
        AUTHENTICATION : 'Token Required'
    */
    @PostMapping("/add")
    public ResponseEntity<String> addWishlistItem(
            @RequestBody WishlistItemAddRequest wishlistItemAddRequest
            ) throws Exception {
        return wishlistService.addWishlistItem(wishlistItemAddRequest);
    }

    /*
        URL : http://localhost:8081/api/Wishlist/delete/{id}
        CONTROLLER FUNCTION : deleteWishlistItem
        DESCRIPTION : This controller function handles requests to delete a specific item from the
                      wishlist of the currently logged-in user. This function  processes the request
                      to remove the specified item from their wishlist.
        PARAMETER : WishlistItemAddRequest { String (wishlistItemName) } | JSON Object
        RETURN : Returns ResponseEntity on successful WishlistItem Deletion, or error response if authentication fails
        AUTHENTICATION : 'Token Required'
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWishlistItem(
            @PathVariable("id") UUID id
            ) throws Exception{
        return wishlistService.deleteWishlistItem(id);
    }
}
