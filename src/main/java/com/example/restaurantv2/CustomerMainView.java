package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerMainView extends AppCompatActivity {

    PHPrequests req=new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");

    Customer customer;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_main_view);

        Intent i=getIntent();
        customer= (Customer) i.getSerializableExtra("Customer");
        getCustomerOrders(customer);

        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            LinearLayout pendingLL=findViewById(R.id.LLPending);
            pendingLL.removeAllViewsInLayout();

            LinearLayout readyLL=findViewById(R.id.LLReady);
            readyLL.removeAllViewsInLayout();

            LinearLayout collectedLL=findViewById(R.id.LLCollected);
            collectedLL.removeAllViewsInLayout();

            getCustomerOrders(customer);

            swipeRefreshLayout.setRefreshing(false);
        });

    }

    public void getCustomerOrders(Customer customer) {
        ContentValues cv = new ContentValues();
        cv.put("ACTION", "getOrderInfo");
        cv.put("CUSTOMER_USERNAME", customer.userName);

        req.doPostRequest(this, "getOrders.php", cv, response -> {
            try {
                processResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    public void processResponse(String response) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        ArrayList<Order>listOfOrders=new ArrayList<>();
        for(int i=0; i<jsonArray.length();++i)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Order order = new Order();
            order.restaurant=jsonObject.get("RESTAURANT").toString();
            order.orderStatus=jsonObject.get("ORDER_STATUS").toString();
            order.empCode=jsonObject.get("STAFF_ID").toString();
            order.orderID= Integer.parseInt(jsonObject.getString("ORDER_ID"));
            order.customerUserName= customer.userName;
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
            if(i.orderStatus.equals("PENDING"))
            {
                LinearLayout pendingLL=findViewById(R.id.LLPending);
                ImageButton block=new ImageButton(this);
                block.setBackgroundResource(R.drawable.customer_box);
                if(i.restaurant.equals("KFC"))
                {
                    block.setImageResource(R.drawable.kfc2);
                    block.setAdjustViewBounds(true);
                }
                else if(i.restaurant.equals("Burger King"))
                {
                    block.setImageResource(R.drawable.burger_king_logo);
                    block.setAdjustViewBounds(true);
                }
                else
                {
                    block.setImageResource(R.drawable.mcdonalds);
                    block.setAdjustViewBounds(true);
                }
                block.setOnClickListener(onClick->{
                    //pass order to get info
                    //set info from intent
                    Intent intent =new Intent(this,CustomerOrderInfo.class);
                    intent.putExtra("Order",i);
                   startActivity(intent);
                });
                pendingLL.addView(block);
            }
            else if(i.orderStatus.equals("READY"))
            {
                LinearLayout readyLL=findViewById(R.id.LLReady);
                ImageButton block=new ImageButton(this);
                block.setBackgroundResource(R.drawable.customer_box);
                if(i.restaurant.equals("KFC"))
                {
                    block.setImageResource(R.drawable.kfc2);
                    block.setAdjustViewBounds(true);
                }
                else if(i.restaurant.equals("Burger King"))
                {
                    block.setImageResource(R.drawable.burger_king_logo);
                    block.setAdjustViewBounds(true);
                }
                else
                {
                    block.setImageResource(R.drawable.mcdonalds);
                    block.setAdjustViewBounds(true);
                }
                block.setOnClickListener(onClick->{
                    //pass order to get info
                    //set info from intent
                    Intent intent =new Intent(this,CustomerOrderInfo.class);
                    intent.putExtra("Order",i);
                    startActivity(intent);
                });
                readyLL.addView(block);
            }
            else
            {
                LinearLayout collectedLL=findViewById(R.id.LLCollected);
                ImageButton block=new ImageButton(this);
                block.setBackgroundResource(R.drawable.customer_box);
                if(i.restaurant.equals("KFC"))
                {
                    block.setImageResource(R.drawable.kfc2);
                    block.setAdjustViewBounds(true);
                }
                else if(i.restaurant.equals("Burger King"))
                {
                    block.setImageResource(R.drawable.burger_king_logo);
                    block.setAdjustViewBounds(true);
                }
                else
                {
                    block.setImageResource(R.drawable.mcdonalds);
                    block.setAdjustViewBounds(true);
                }
                block.setOnClickListener(onClick->{
                    Intent intent =new Intent(this,CustomerOrderInfo.class);
                    intent.putExtra("Order",i);
                    startActivity(intent);
                });
                collectedLL.addView(block);
            }

        }
    }
}