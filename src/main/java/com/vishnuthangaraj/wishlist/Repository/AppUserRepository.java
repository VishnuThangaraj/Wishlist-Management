package com.vishnuthangaraj.wishlist.Repository;

import com.vishnuthangaraj.wishlist.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    /*
        REPOSITORY FUNCTION : findByEmail
        DESCRIPTION : This method is a custom query method defined within the AppUserRepository interface,
                      utilizing the @Query annotation with native SQL to retrieve an Optional<AppUser>
                      based on the provided email address. This method is particularly useful for querying
                      the database to find a user entity by their unique email identifier.
        PARAMETER : - `email` (String): The email address used as a unique identifier to locate user in the database.
    */
    @Query(value = "SELECT * FROM app_user where email =:email", nativeQuery = true)
    Optional<AppUser> findByEmail(String email);

    /*
        REPOSITORY FUNCTION : getUserByEmail
        DESCRIPTION : This function is a custom query method defined within the repository associated with
                      the `AppUser` entity. This function retrieves a user from the database
                      based on their email address. The function executes a native SQL query,
                      allowing direct interaction with the underlying database to efficiently
                      fetch user details matching the specified email.
        PARAMETER : - `email` (String): The email address used as a unique identifier to locate user in the database.
    */
    @Query(value = "SELECT * FROM app_user where email =:email", nativeQuery = true)
    AppUser getUserByEmail(String email);
}
