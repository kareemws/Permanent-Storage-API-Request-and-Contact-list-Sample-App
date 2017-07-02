package com.kareemwaleed.arxicttask.database;

/**
 * Created by Kareem Waleed on 7/2/2017.
 */

public class UserModel {
    private String fullName;
    private String emailAddress;
    private String password;

    public UserModel(String fullName, String emailAddress, String password){
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
