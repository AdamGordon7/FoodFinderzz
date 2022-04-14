package com.example.restaurantv2;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    String fName;
    String lName;
    String userName;
    String password;
    Order order;

    ArrayList<Customer>currentlyLoggedIn=new ArrayList<>();

    public Customer(String fName, String lName, String userName, String password)
    {
        this.fName=fName;
        this.lName=lName;
        this.userName=userName;
        this.password=password;
    }

}
