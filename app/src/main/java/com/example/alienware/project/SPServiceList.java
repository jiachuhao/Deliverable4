package com.example.alienware.project;

public class SPServiceList extends ServicesList {
    private String id;
    private String name;
    private String hourlyRate;

    public SPServiceList(){

    }
    public SPServiceList(String id,String name, String hourlyRate){
        this.id=id;
        this.name=name;
        this.hourlyRate=hourlyRate;

    }
    public String getId() {

        return id;
    }
    public String getName() {

        return name;
    }

    public String getHourlyRate() {

        return hourlyRate;
    }
}
