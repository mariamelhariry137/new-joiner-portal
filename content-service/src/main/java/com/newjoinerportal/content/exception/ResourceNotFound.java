package com.newjoinerportal.content.exception;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String msg){
        super(msg);
    }
    public ResourceNotFound(String name, Long id){
        super(String.format("%s is not found with id %s", name, id));
    }
}
