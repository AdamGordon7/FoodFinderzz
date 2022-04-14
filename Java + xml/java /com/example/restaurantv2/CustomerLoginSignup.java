package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import org.json.JSONStringer;

import java.util.ArrayList;

public class CustomerLoginSignup extends AppCompatActivity {
    PHPrequests req = new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_login_signup);

        doLogin();
    }

    public void doCustomerSignup(View v) {
        Intent intent = new Intent(CustomerLoginSignup.this, CustomerSignUp.class);
        startActivity(intent);
    }

    public boolean validateInput(String userName, EditText userNameET, String password, EditText passwordET) {

        if (password.isEmpty()) {
            passwordET.setError("Password cannot be empty");
            passwordET.requestFocus();
            return false;
        }
        if (userName.isEmpty()) {
            userNameET.setError("User Name cannot be empty");
            userNameET.requestFocus();
            return false;
        }

        return true;
    }

    public void doLogin() {
        Button logIn = findViewById(R.id.buttonCustomerDoLogIn);
        logIn.setOnClickListener(onClick -> {

            EditText etUserName = findViewById(R.id.editTextCustomerLogInUserName);
            EditText etPassword = findViewById(R.id.editTextCustomerLogInPassword);

            String userName = etUserName.getText().toString();
            String passwrd = etPassword.getText().toString();

            if (validateInput(userName, etUserName, passwrd, etPassword)) {

                ContentValues cv = new ContentValues();
                cv.put("CUSTOMER_USERNAME", userName);
                cv.put("passwrd", passwrd);
                cv.put("userType", "CUSTOMER");

                req.doPostRequest(CustomerLoginSignup.this, "login.php", cv, response -> {
                    Log.d("REsp","ONREPONSE "+response);
                    processLogin(response);
                });

            }

        });


    }

    public void processLogin(String response){

        try{
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            customer = new Customer(jsonObject.getString("F_NAME"),jsonObject.getString("L_NAME"),jsonObject.getString("CUSTOMER_USERNAME"), jsonObject.getString("passwrd"));


            Intent intent = new Intent(CustomerLoginSignup.this, CustomerMainView.class);
            intent.putExtra("Customer",customer);
            startActivity(intent);

        }
        catch(JSONException e)
        {
            Toast.makeText(this,"Incorrect username or password, Please try again!",Toast.LENGTH_SHORT).show();

        }

    }
}

