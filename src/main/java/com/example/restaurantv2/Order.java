package com.example.restaurantv2;

import android.util.Log;

import java.io.Serializable;

public class Order implements Serializable {

    int orderID;
    String empCode;
    String restaurant;
    String orderStatus;
    String customerUserName;
    String orderTime;
    String rating;

    public void print()
    {
        Log.d("ORDER","orderID "+orderID);
        Log.d("ORDEr","empCode "+empCode);
        Log.d("ORDER","restaurant "+restaurant);
        Log.d("ORDER","status "+orderStatus);
        Log.d("ORDER","cusername "+customerUserName);
        Log.d("ORDER","time "+orderTime);
        Log.d("ORDER","rating "+rating);
    }

}
