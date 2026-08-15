package com.newjoinerportal.content.exception;

public class DuplicateResource extends RuntimeException{
    public DuplicateResource(String msg){
        super(msg);
    }
}
