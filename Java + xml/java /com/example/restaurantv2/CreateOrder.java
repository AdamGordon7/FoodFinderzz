package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CreateOrder extends AppCompatActivity {

    String currentDateTime;
    Staff staff;
    PHPrequests req = new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");

    ArrayList<String>takenOrderID=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_order);

        currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
        TextView dateTV = findViewById(R.id.textViewDateTime);
        dateTV.setText(currentDateTime);


        Intent i=getIntent();
        staff= (Staff) i.getSerializableExtra("Staff");

        ContentValues cv=new ContentValues();
        cv.put("userType","ORDERS");
        req.doPostRequest(this,"checkUniqueOrderID.php",cv,response -> {
            try {
                processUnique(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        ContentValues ratingCv=new ContentValues();
        ratingCv.put("STAFF_ID",staff.empCode);
        req.doPostRequest(this,"getAvgRating.php",ratingCv,response -> {
            try {
                processStaffRating(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        createOrder(staff);

    }

    public boolean validateInput(String customerUserName, EditText customerUserNameET) {

        if (customerUserName.isEmpty()) {
            customerUserNameET.setError("cannot be empty");
            customerUserNameET.requestFocus();
            return false;
        }
        return true;
    }


    public void createOrder(Staff staff) {
        EditText customerUserNameET = findViewById(R.id.editTextCustomerUserName);
        TextView empCodeET = (TextView)findViewById(R.id.textViewEmpCode);
        TextView restaurant = findViewById(R.id.textViewRestaurant);

        empCodeET.setText("Staff ID: " + staff.empCode);



        Button logOrder = findViewById(R.id.buttonLogOrder);
        logOrder.setOnClickListener(onClick -> {

            String userName = customerUserNameET.getText().toString();

            if (validateInput(userName, customerUserNameET)) {
                if (staff.empCode.charAt(0) == 'M') {
                    restaurant.setText("McDonalds");

                } else if (staff.empCode.charAt(0) == 'K') {
                    restaurant.setText("KFC");
                } else {
                    restaurant.setText("Burger King");
                }
            }

            String orderId=generateOrderId();

            for(int i=0; i<takenOrderID.size();++i)
            {
                if(orderId.equals(i))
                {
                    orderId=generateOrderId();
                }
            }

            ContentValues cv = new ContentValues();
            cv.put("ACTION", "placeOrder");
            cv.put("CUSTOMER_USERNAME", userName);
            cv.put("ORDER_ID", orderId.toString());
            cv.put("STAFF_ID", staff.empCode);
            cv.put("RESTAURANT", staff.restaurant);
            cv.put("ORDER_STATUS", "PENDING");
            cv.put("ORDER_TIME", currentDateTime);

            req.doPostRequest(this, "getOrders.php", cv, response -> {
                proccessResponse(response);
            });

        });
    }

    public void proccessResponse(final String response) {
        if (!response.equals("Fail")) {
            Toast.makeText(this, "Successfully logged order", Toast.LENGTH_SHORT).show();
        }
    }

    public void processUnique(String response) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        for(int i=0; i<jsonArray.length();++i)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String userName=jsonObject.getString("ORDER_ID");
            takenOrderID.add(userName);
        }
    }

    public String generateOrderId()
    {
        StringBuilder orderId = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            String digit = Integer.toString(rand.nextInt(10));
            orderId.append(digit);
        }
        return orderId.toString();
    }

    public void processStaffRating(String response) throws JSONException {

        TextView staffRatingTV=findViewById(R.id.staffRating);
        JSONArray jsonArray=new JSONArray(response);
        JSONObject jsonObject=jsonArray.getJSONObject(0);
        String staffRating=jsonObject.getString("AVG(RATING)");

        if (!staffRating.equals("null"))
        {
            staffRating=jsonObject.getString("AVG(RATING)");
            Double staffRatingd=Double.parseDouble(staffRating)*100;
            staffRatingTV.setText(" Avg Rating \n " + String.valueOf(staffRatingd));

        }
        else {
            staffRatingTV.setText(" Avg Rating \n "+ staffRating);
            staffRatingTV.setTextSize(10);
            staffRatingTV.setGravity(Gravity.CENTER);
        }
    }
}
