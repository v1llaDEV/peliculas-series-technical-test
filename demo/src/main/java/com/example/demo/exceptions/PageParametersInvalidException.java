package com.example.demo.exceptions;

import java.io.IOException;

public class PageParametersInvalidException extends IOException {
    public PageParametersInvalidException(String message){
        super(message);
    };
}
