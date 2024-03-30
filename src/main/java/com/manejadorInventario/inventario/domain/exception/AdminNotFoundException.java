package com.manejadorInventario.inventario.domain.exception;

public class AdminNotFoundException extends RuntimeException{

    public AdminNotFoundException(String message){
        super(message);
    }
}
