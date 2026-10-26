package com.bgl.medilabosolutions.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String email) {
        super("Email déjà utilisé");
    }
}
