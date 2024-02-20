package com.vishnuthangaraj.wishlist.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vishnuthangaraj.wishlist.Enums.Gender;
import com.vishnuthangaraj.wishlist.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {

    /*
        CLASS NAME : AppUser
        DESCRIPTION : This class represents a model entity that encapsulates essential information
                      about a user within the application. This model integrates with Spring Security's
                      `UserDetails` interface, enabling seamless integration with authentication and
                      authorization mechanisms. Instances of this class store user details,
                      including their unique identifier, name, gender, role, email, password,
                      and a collection of wishlist items associated with the user.
        ATTRIBUTES : - `id` (UUID): Unique identifier generated for each user.
                     - `name` (String): Represents the full name of the user.
                     - `gender` (Gender): Enumerated type capturing the user's gender.
                     - `role` (Role): Enumerated type representing the user's role in the application (e.g., USER or ADMIN).
                     - `email` (String): Unique identifier for the user's account, used for authentication and communication.
                     - `password` (String): Securely stores the user's hashed password for authentication.
                     - `wishlistItems` (List<WishlistItem>): Collection of wishlist items associated with the user.
    */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<WishlistItem> wishlistItems;

    // UserDetails (Implementation Interface)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
