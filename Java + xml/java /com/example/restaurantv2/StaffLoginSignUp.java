package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StaffLoginSignUp extends AppCompatActivity {
    PHPrequests req=new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_staff_login_sign_up);
        doLogin();
    }

    public void doStaffSignUp(View v)
    {
        Intent intent= new Intent(StaffLoginSignUp.this,StaffSignUp.class);
        startActivity(intent);
    }

    public boolean validateInput(String userName, EditText userNameET, String password, EditText passwordET)
    {

        if(password.isEmpty())
        {
            passwordET.setError("Password cannot be empty");
            passwordET.requestFocus();
            return false;
        }
        if(userName.isEmpty())
        {
            userNameET.setError("User Name cannot be empty");
            userNameET.requestFocus();
            return false;
        }

        return true;
    }


    public void doLogin()
    {
        Button login=findViewById(R.id.buttonStaffDoLogIn);

        login.setOnClickListener(onClick->{

            EditText etEmpCode=findViewById(R.id.editTextStaffLogInEmpCode);
            EditText etPassword=findViewById(R.id.editTextStaffLogInPassword);

            String empCode=etEmpCode.getText().toString();
            String passwrd=etPassword.getText().toString();

            if(validateInput(empCode,etEmpCode,passwrd,etPassword))
            {
                ContentValues cv=new ContentValues();
                cv.put("STAFF_ID",empCode);
                cv.put("passwrd",passwrd);
                cv.put("userType","STAFF");

                req.doPostRequest(this,"staffLogin.php",cv,response -> {
                    processLogin(response);
                });
            }
        });
    }

    public void processLogin(String response) {
        try{
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            staff = new Staff(jsonObject.getString("F_NAME"),jsonObject.getString("L_NAME"),jsonObject.getString("STAFF_ID"), jsonObject.getString("passwrd"),jsonObject.getString("RESTAURANT"));


            Intent intent = new Intent(this, StaffMainView.class);
            intent.putExtra("Staff",staff);
            startActivity(intent);
        }catch (JSONException e)
        {
            Toast.makeText(this,"Incorrect staff ID or password, Please try again!",Toast.LENGTH_SHORT).show();

        }
    }
}