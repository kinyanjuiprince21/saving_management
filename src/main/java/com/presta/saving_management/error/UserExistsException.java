package com.presta.saving_management.error;

public class UserExistsException extends Throwable{

    public UserExistsException(String message) {
        super(message);
    }
}
