package com.gleb.web.file;

public enum FileType {
    DEFAULT("index.html"),
    NOT_FOUND("404.html"),
    INTERNAL_SERVER_ERROR("503.html");

    private String name;

    FileType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
