package com.ricardo.scalable.ecommerce.platform.userService.exceptions;

public class PasswordDoNotMatchException  extends Exception{

    public PasswordDoNotMatchException(String message) {
        super(message);
    }

    public PasswordDoNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordDoNotMatchException(Throwable cause) {
        super(cause);
    }

}
