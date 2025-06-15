package com.Cobra.EvoCommerce.Exception;

public class CustomDuplicateEmailException extends RuntimeException {
    public CustomDuplicateEmailException(String message) {
        super(message);
    }
}
