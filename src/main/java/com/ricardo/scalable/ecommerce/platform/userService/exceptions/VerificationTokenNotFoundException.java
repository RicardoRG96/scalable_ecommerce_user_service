package com.ricardo.scalable.ecommerce.platform.userService.exceptions;

public class VerificationTokenNotFoundException extends RuntimeException {

    public VerificationTokenNotFoundException(String message) {
        super(message);
    }

    public VerificationTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationTokenNotFoundException(Throwable cause) {
        super(cause);
    }

}
