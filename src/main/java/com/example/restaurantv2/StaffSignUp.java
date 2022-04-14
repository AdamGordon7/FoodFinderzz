package com.example.restaurantv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class StaffSignUp extends AppCompatActivity {

    PHPrequests req = new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    ArrayList<String>takenUserNames=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_staff_sign_up);


        //on create making a list of taken usernames which can then compare generate dusrname to
        ContentValues cv=new ContentValues();
        cv.put("userType","STAFF");
        req.doPostRequest(this,"checkUniqueStaffID.php",cv,response -> {
            try {
                processUnique(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        staffDropDown();
        userInfoCapture();
    }

    public boolean validateInput(String fName, EditText staffFnameET, String lName, EditText staffLnameET, AutoCompleteTextView menu, String menuChoice, EditText password, String passwordText) {
        if (fName.isEmpty()) {
            staffFnameET.setError("Name cannot be blank");
            staffFnameET.requestFocus();
            return false;
        }
        if (lName.isEmpty()) {
            staffLnameET.setError("Last name cannot be empty");
            staffLnameET.requestFocus();
            return false;
        }
        if (menuChoice.isEmpty()) {
            menu.setError("Invalid selection");
            menu.requestFocus();
            return false;
        }
        if (passwordText.isEmpty()) {
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        }

        return true;
    }

    public void userInfoCapture() {
        EditText staffFnameET = findViewById(R.id.editTextStaffFname);
        EditText staffLanmeET = findViewById(R.id.editTextStaffLname);
        EditText staffPassword = findViewById(R.id.editTextStaffSignUpPassword);
        AutoCompleteTextView menu = findViewById(R.id.autoCompleteTextViewDropDown);


        Button doSignUp = (Button) findViewById(R.id.buttonDoStaffSignUp);
        doSignUp.setOnClickListener(onClick ->
        {
            boolean btnCounter = false;
            String fName = staffFnameET.getText().toString();
            String lName = staffLanmeET.getText().toString();
            String choice = menu.getText().toString();
            String password = staffPassword.getText().toString();
            if (validateInput(fName, staffFnameET, lName, staffLanmeET, menu, choice, staffPassword, password)) {
                String empCode = generateEmpCode();

                for(String staffID: takenUserNames)
                {
                    if(empCode.equals(staffID))
                    {
                        empCode=generateEmpCode();
                    }
                }

                Toast.makeText(StaffSignUp.this, empCode, Toast.LENGTH_LONG)
                        .show();

                Staff staff = new Staff(fName, lName, empCode, password, choice);

                ContentValues cv = new ContentValues();
                cv.put("F_NAME", fName);
                cv.put("L_NAME", lName);
                cv.put("STAFF_ID", empCode);
                cv.put("passwrd", password);
                cv.put("RESTAURANT",choice);

                req.doPostRequest(this, "staffSignUp.php", cv, response -> {});
                btnCounter = true;
            }
            if (btnCounter) {
                doSignUp.setEnabled(false);
            }
        });

    }

    public String generateEmpCode() {
        StringBuilder empCode = new StringBuilder();

        AutoCompleteTextView menu = findViewById(R.id.autoCompleteTextViewDropDown);
        String choice = menu.getText().toString();

        switch (choice) {
            case "KFC":
                empCode.append("KF");
                break;
            case "McDonalds":
                empCode.append("MD");
                break;
            case "Burger King":
                empCode.append("BK");
                break;
        }


            for (int i = 0; i < 4; i++)
            {
                Random rand = new Random();
                String digit = Integer.toString(rand.nextInt(10));
                empCode.append(digit);
            }


        return empCode.toString();
    }

    public void staffDropDown() {
        // TextInputLayout dropDownBack;
        AutoCompleteTextView dropDownMenu;

        ArrayList<String> arrayListMenu;
        ArrayAdapter<String> arrayAdapterMenu;

        dropDownMenu = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewDropDown);

        arrayListMenu = new ArrayList<>();
        arrayListMenu.add("KFC");
        arrayListMenu.add("McDonalds");
        arrayListMenu.add("Burger King");

        arrayAdapterMenu = new ArrayAdapter<>(getApplication(), R.layout.restaurant_entity, arrayListMenu);
        dropDownMenu.setAdapter(arrayAdapterMenu);
        dropDownMenu.setThreshold(1);
    }

    public void processUnique(String response) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        for(int i=0; i<jsonArray.length();++i)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String userName=jsonObject.getString("STAFF_ID");
            takenUserNames.add(userName);
        }
    }
}
