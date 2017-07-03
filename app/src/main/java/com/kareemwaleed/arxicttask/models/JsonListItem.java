package com.kareemwaleed.arxicttask.models;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public class JsonListItem {
    private String userID;
    private String ID;
    private String title;
    private String body;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
