package com.vishnuthangaraj.wishlist.Security.Configuration;

import com.vishnuthangaraj.wishlist.Repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final AppUserRepository appUserRepository;

    /*
        FUNCTION NAME : userDetailsService
        DESCRIPTION : This method, annotated with @Bean, defines the implementation of the UserDetailsService
                       interface. This bean is responsible for loading user details by username
                       from the application's repository (appUserRepository). If a user with the given
                       username (email) is not found, a UsernameNotFoundException is thrown.
        PARAMETER : 'null'
    */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> appUserRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /*
        FUNCTION NAME : authenticationProvider
        DESCRIPTION : This method is annotated with @Bean, defines the configuration of the authentication
                      provider used by Spring Security. It utilizes the DaoAuthenticationProvider,
                      specifying the user details service (userDetailsService())
                      and password encoder (passwordEncoder()).
        PARAMETER : 'null'
    */
    @Bean
    public AuthenticationProvider authenticationProvider(){

        // Use to fetch user Details and Encrypt password
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /*
        FUNCTION NAME : authenticationProvider
        DESCRIPTION : method, annotated with @Bean, defines the configuration for the authentication
                      manager bean in Spring Security. It retrieves the authentication manager
                      instance from the provided AuthenticationConfiguration.
        PARAMETER : AuthenticationConfiguration (config)
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
        FUNCTION NAME : passwordEncoder
        DESCRIPTION : This method is annotated with @Bean, defines the configuration for the password encoder
                      used in Spring Security. It produces an instance of the BCryptPasswordEncoder,
                      a widely used password hashing algorithm that provides security by incorporating salt.
        PARAMETER : 'null'
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
