package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class CustomerSignUp extends AppCompatActivity {
    PHPrequests req=new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    ArrayList<String>takenUserNames=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_sign_up);

        ContentValues cv=new ContentValues();
        cv.put("userType","CUSTOMER");
        req.doPostRequest(this,"checkUniqueUserName.php",cv,response -> {
            try {
                processUnique(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        userInfoCapture();
    }

    //try make neater if can?
    public boolean validateInput(String fName, EditText customerFnameET, String lName, EditText customerLnameET,String userName, EditText userNameET,String password, EditText passwordET)
    {
        if(fName.isEmpty())
        {
            customerFnameET.setError("Name cannot be blank");
            customerFnameET.requestFocus();
            return false;
        }
        if(lName.isEmpty())
        {
            customerLnameET.setError("Last name cannot be empty");
            customerLnameET.requestFocus();
            return false;
        }

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
        for(String i : takenUserNames)
        {
            if(userName.equals(i))
            {
                userNameET.setError("Username is taken");
                userNameET.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void userInfoCapture()
    {
        //onclick listner  only avalibale when validated input
        EditText customerFnameET=findViewById(R.id.editTextCustomerFname);
        EditText customerLanmeET=findViewById(R.id.editTextCustomerLname);
        EditText customerPasswordET=findViewById(R.id.editTextCustomerSignUpPassword);
        EditText customerUserNameET=findViewById(R.id.editTextCustomerSignUpUserName);



        Button doSignUp=(Button)findViewById(R.id.buttonDoCustomerSignUp);
        doSignUp.setOnClickListener(onClick ->
        {
            boolean btnCounter=false;
            String fName=customerFnameET.getText().toString();
            String lName=customerLanmeET.getText().toString();
            String userName=customerUserNameET.getText().toString();
            String password=customerPasswordET.getText().toString();

            if(validateInput(fName, customerFnameET,lName, customerLanmeET,userName,customerUserNameET,password,customerPasswordET))
            {

                Customer customer=new Customer(fName,lName,userName,password);
                ContentValues cv=new ContentValues();
                cv.put("F_NAME",fName);
                cv.put("L_NAME",lName);
                cv.put("CUSTOMER_USERNAME",userName);
                cv.put("passwrd",password);
                cv.put("userType","CUSTOMER");

                req.doPostRequest(this,"customerSignUp.php",cv,response -> {
                    processResponse(response);
                });

                btnCounter=true;
            }
            if(btnCounter)
            {
                doSignUp.setEnabled(false);
            }
        });
    }

    public void processResponse(final String response)
    {
        if(!response.equals("fail"))
        {
            Toast.makeText(this,"Successful Sign Up!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Unsuccessful Sign Up!",Toast.LENGTH_SHORT).show();

        }
    }

    public void processUnique(String response) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        for(int i=0; i<jsonArray.length();++i)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String userName=jsonObject.getString("CUSTOMER_USERNAME");
            takenUserNames.add(userName);
        }
    }
}