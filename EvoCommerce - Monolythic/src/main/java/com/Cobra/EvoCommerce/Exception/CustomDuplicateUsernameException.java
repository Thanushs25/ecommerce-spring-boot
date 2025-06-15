package com.Cobra.EvoCommerce.Exception;

public class CustomDuplicateUsernameException extends RuntimeException{
    public CustomDuplicateUsernameException(String message){
        super(message);
    }
}
