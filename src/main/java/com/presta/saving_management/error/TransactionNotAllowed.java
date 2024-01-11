package com.presta.saving_management.error;

public class TransactionNotAllowed extends Throwable{

    public TransactionNotAllowed(String message) {
        super(message);
    }
}
