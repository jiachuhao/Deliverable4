package com.example.alienware.project;

public class Account {

    private String id;
    private String email;
    private String password;
    private String accountType;

    public Account(){

    }
    public Account(String id, String email, String password,String accountType){
        this.id=id;
        this.email=email;
        this.password=password;
        this.accountType=accountType;

    }
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }
}
