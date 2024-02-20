package com.vishnuthangaraj.wishlist.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {

    /*
        CLASS NAME : WishlistItem
        DESCRIPTION : This class represents a model entity that encapsulates information about an item in a
                      user's wishlist. Instances of this class store details such as a unique identifier (`id`),
                      the name of the wishlist item (`itemName`), an optional description, and a reference
                      to the associated `AppUser`. Each wishlist item is uniquely identified within the application,
                      and its details are linked to a specific user's wishlist.
        ATTRIBUTES : - `id` (UUID): Unique identifier generated for each wishlist item.
                     - `itemName` (String): Represents the name or description of the wishlist item.
                     - `description` (String): Optional additional information or details about the wishlist item.
                     - `appUser` (AppUser): Many-to-one relationship with the `AppUser` entity, linking the wishlist item to a specific user.
    */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String itemName;

    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "appUser_id", nullable = false)
    private AppUser appUser;
}
