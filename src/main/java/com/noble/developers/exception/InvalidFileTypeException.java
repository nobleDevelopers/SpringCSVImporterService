package com.noble.developers.exception;

public class InvalidFileTypeException extends RuntimeException{
    public InvalidFileTypeException(String message){
        super(message);
    }
}
