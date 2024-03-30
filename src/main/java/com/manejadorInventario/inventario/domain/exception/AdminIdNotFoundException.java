package com.manejadorInventario.inventario.domain.exception;

public class AdminIdNotFoundException extends RuntimeException {

    public AdminIdNotFoundException(String message){
        super(message);
    }
}