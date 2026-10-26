package com.bgl.medilabosolutions.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super("Utilisateur non trouvé : " + message);
    }
}
