package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaffMainView extends AppCompatActivity {

    PHPrequests req = new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    Staff staff;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_staff_main_view);

        Intent i=getIntent();
        staff= (Staff) i.getSerializableExtra("Staff");
        getStaffOrders();


        swipeRefreshLayout=findViewById(R.id.swipeRefreshStaff);
        swipeRefreshLayout.setOnRefreshListener(() -> {


            LinearLayout pendingLL=findViewById(R.id.staffMainLL);
            pendingLL.removeAllViewsInLayout();

            ContentValues cv=new ContentValues();
            cv.put("STAFF_ID",staff.empCode);

            req.doPostRequest(this,"staffOrders.php",cv,response -> {
                try {
                    processResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            swipeRefreshLayout.setRefreshing(false);
        });


    }

    public void addCustomer(View v)
    {
        //carry employee deets over for auto completes.
        Intent intent=new Intent(this,CreateOrder.class);
        intent.putExtra("Staff",staff);
        startActivity(intent);
    }

    public void getStaffOrders()
    {
        ContentValues cv=new ContentValues();
        cv.put("STAFF_ID",staff.empCode);

        req.doPostRequest(this,"staffOrders.php",cv,response -> {
            try {
                processResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


    }

    public void processResponse(String response) throws JSONException {
        JSONArray jsonArray=new JSONArray(response);
        ArrayList<Order> listOfOrders=new ArrayList<>();
        for(int i=0; i<jsonArray.length();++i)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Order order = new Order();
            order.restaurant=jsonObject.get("RESTAURANT").toString();
            order.orderStatus=jsonObject.get("ORDER_STATUS").toString();
            order.empCode=jsonObject.get("STAFF_ID").toString();
            order.orderID= Integer.parseInt(jsonObject.getString("ORDER_ID"));
            order.customerUserName= jsonObject.getString("CUSTOMER_USERNAME");
            order.orderTime=jsonObject.get("ORDER_TIME").toString();
            order.rating=jsonObject.get("RATING").toString();
            listOfOrders.add(order);
        }
        createButtons(listOfOrders);


    }

    public void createButtons(ArrayList<Order> orders)
    {
        for(Order i : orders)
        {
            LinearLayout pendingLL=findViewById(R.id.staffMainLL);
            TextView block=new TextView(this);
            block.setBackgroundResource(R.drawable.customer_box);
            block.setText("Order ID: "+ i.orderID + "\n" + "Customer User Name: " + i.customerUserName + "\n" + "Order status: " + i.orderStatus);
            block.setTextSize(14);
            block.setTextColor(Color.parseColor("#2A323D"));
            block.setOnClickListener(onClick->{
                Intent intent=new Intent(this,ChangeStatus.class);
                intent.putExtra("Order",i);
                startActivity(intent);
            });
            pendingLL.addView(block);
        }
    }

}