package com.bgl.medilabosolutions.security;

import com.bgl.medilabosolutions.entities.UserEntity;
import com.bgl.medilabosolutions.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    //private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //logger.warn(">>> TEMP loadUserByUsername appelé avec : [{}]", email);

        UserEntity user = userRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> {
                    //logger.warn(">>> TEMP utilisateur introuvable pour : [{}]", email);
                    return new UsernameNotFoundException("Identifiants invalides");
                });

        //logger.warn(">>> TEMP utilisateur trouvé, rôle : {}", user.getRole());

        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
