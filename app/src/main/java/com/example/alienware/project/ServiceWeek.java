package com.example.alienware.project;

public class ServiceWeek {
    private String id;
    private String name;
    private String week;

    public ServiceWeek(){

    }
    public ServiceWeek(String id,String name,String week){
        this.id=id;
        this.name=name;
        this.week=week;


    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getWeek() {
        return week;
    }
}
