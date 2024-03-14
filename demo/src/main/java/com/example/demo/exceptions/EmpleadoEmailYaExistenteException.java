package com.example.demo.exceptions;

public class EmpleadoEmailYaExistenteException extends RuntimeException {

    public EmpleadoEmailYaExistenteException(){};

    public EmpleadoEmailYaExistenteException(String message) {
        super(message);
    }
}
