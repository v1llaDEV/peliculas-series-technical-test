package com.example.demo.exceptions;

public class CustomInvalidParameterException extends RuntimeException{
    public CustomInvalidParameterException(String message){
        super(message);
    };
}
