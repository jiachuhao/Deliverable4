package com.example.alienware.project;

public class Service_Provider_Account {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String nameOfCompany;
    private String generalDescription;
    private String licensed;


    public Service_Provider_Account(){

    }


    public Service_Provider_Account(String id,String name, String address, String phoneNumber,String nameOfCompany,String generalDescription,String licensed){
        this.id=id;
        this.name=name;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.nameOfCompany=nameOfCompany;
        this.generalDescription=generalDescription;
        this.licensed=licensed;


    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getNameOfCompany() {
        return nameOfCompany;
    }
    public String getGeneralDescription() {
        return generalDescription;
    }
    public String getLicensed() {
        return licensed;
    }

}
