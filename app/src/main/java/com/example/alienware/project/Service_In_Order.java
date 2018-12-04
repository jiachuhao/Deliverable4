package com.example.alienware.project;

public class Service_In_Order {

    private String service_name;
    private String home_owner_id;
    private String home_owner_name;
    private String service_provider_id;
    private String service_provider_name;
    private String rated;

    public Service_In_Order(){

    }
    public Service_In_Order(String service_name,String home_owner_id,String home_owner_name,String service_provider_id,String service_provider_name,String rated){
        this.service_name=service_name;
        this.home_owner_id=home_owner_id;
        this.home_owner_name=home_owner_name;
        this.service_provider_id=service_provider_id;
        this.service_provider_name=service_provider_name;
        this.rated=rated;


    }

    public String getService_name() {
        return service_name;
    }



    public String getHome_owner_id() {
        return home_owner_id;
    }


    public String getHome_owner_name() {
        return home_owner_name;
    }



    public String getService_provider_id() {
        return service_provider_id;
    }



    public String getService_provider_name() {
        return service_provider_name;
    }
    public String getRated() {
        return rated;
    }



}
