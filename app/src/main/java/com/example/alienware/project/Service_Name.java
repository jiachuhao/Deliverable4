package com.example.alienware.project;

public class Service_Name {

    private String id;
    private String name;
    private String service;

    public Service_Name(){

    }
    public Service_Name(String id,String name,String service){
        this.id=id;
        this.name=name;
        this.service=service;


    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getService() {
        return service;
    }
}
