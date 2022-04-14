package com.example.restaurantv2;

import java.io.Serializable;
import java.util.ArrayList;

public class Staff implements Serializable {

    //not sure password safety reasons
    String password;
    String fName;
    String lName;
    String empCode;
    String restaurant;

    public Staff(String fName, String lName, String empCode, String password, String restaurant)
    {
        this.fName=fName;
        this.lName=lName;
        this.empCode=empCode;
        this.password=password;
        this.restaurant=restaurant;


    }
    public Staff(){}


}

