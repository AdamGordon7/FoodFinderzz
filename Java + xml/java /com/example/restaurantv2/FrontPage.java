package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FrontPage extends AppCompatActivity {

    PHPrequests req=new PHPrequests("https://lamp.ms.wits.ac.za/home/s2275253/");
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_front_page);
    }


    public void doCustomer(View v)
    {
        Intent intent=new Intent(FrontPage.this,CustomerLoginSignup.class);
        startActivity(intent);
    }

    public void doStaff(View v)
    {
        Intent intent=new Intent(FrontPage.this,StaffLoginSignUp.class);
        startActivity(intent);

    }

}