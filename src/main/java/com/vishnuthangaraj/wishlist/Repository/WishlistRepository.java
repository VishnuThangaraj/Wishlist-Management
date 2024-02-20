package com.vishnuthangaraj.wishlist.Repository;

import com.vishnuthangaraj.wishlist.Model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, UUID> {

    /*
        INTERFACE NAME : WishlistRepository
        DESCRIPTION : This interface serves as a Spring Data JPA repository interface, extending the
                      `JpaRepository` interface and providing data access operations for
                      the `WishlistItem` entity. This interface inherits a set of standard
                      CRUD (Create, Read, Update, Delete) methods from `JpaRepository`,
                      enabling seamless interaction with the underlying database for managing wishlist items.

    */

}
