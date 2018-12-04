package com.example.alienware.project;

public class Service_Provider_Rate {

    private double finalrate;
    private double rate;
    private int times;
    private String id;
    private String name;

    public Service_Provider_Rate(){

    }

    public Service_Provider_Rate(String name){
        this.name=name;

    }
    public Service_Provider_Rate(double finalrate,double rate,int times,String id,String name){
        this.finalrate=finalrate;
        this.rate=rate;
        this.times=times;
        this.id=id;
        this.name=name;



    }
    public double getFinalrate() {
        return finalrate;
    }
    public double getRate() {
        return rate;
    }
    public int getTimes() {
        return times;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }

}
