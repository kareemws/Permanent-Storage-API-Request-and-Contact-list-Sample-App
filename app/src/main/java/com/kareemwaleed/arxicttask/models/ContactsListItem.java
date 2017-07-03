package com.kareemwaleed.arxicttask.models;

import java.util.ArrayList;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public class ContactsListItem {
    private String name;
    private ArrayList<String> numbers;

    public ContactsListItem(){
        numbers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public void addNumber(String number) {
        numbers.add(number);
    }
}
