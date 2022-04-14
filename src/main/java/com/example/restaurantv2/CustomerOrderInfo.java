package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CustomerOrderInfo extends AppCompatActivity {

    PHPrequests req=new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_info);

        Intent i=getIntent();
        order= (Order) i.getSerializableExtra("Order");
        setData(order);
    }

    public void setData(Order order)
    {
        //staff memember need to make new query to get name
        TextView staffMemb=findViewById(R.id.popStaffMemb);
        TextView orderTime=findViewById(R.id.popOrderTime);
        TextView avgRating=findViewById(R.id.popAvgRating);
        TextView orderId=findViewById(R.id.popOrderId);

        TextView close=findViewById(R.id.txtClose);
        ImageButton thumbsUp=findViewById(R.id.thumbsUp);
        ImageButton thumbsDown=findViewById(R.id.thumbsDown);



        ContentValues cv5=new ContentValues();
        cv5.put("STAFF_ID",order.empCode);
        req.doPostRequest(this,"staffInfo.php",cv5,response ->{
            try {
                processStaffInfo(response,staffMemb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        ImageView logo=findViewById(R.id.logo);
        if(order.restaurant.equals("KFC"))
        {
            logo.setImageResource(R.drawable.kfc2);
        }
        else if(order.restaurant.equals("McDonalds"))
        {
            logo.setImageResource(R.drawable.mcdonalds);
        }
        else
        {
            logo.setImageResource(R.drawable.burger_king_logo);
        }

        ContentValues cv2=new ContentValues();
        cv2.put("STAFF_ID",order.empCode);
        req.doPostRequest(this,"getAvgRating.php",cv2,response ->{
            try {
                processGetAvgRating(response,avgRating);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });




        orderTime.setText(order.orderTime);
        //avgRating.setText(order);
        //need to make request for avg rating as not order rating
        orderId.setText(String.valueOf(order.orderID));
        orderTime.setText(order.orderTime);

        close.setOnClickListener(onClick->{
            Intent intent=new Intent(this,CustomerMainView.class);
            startActivity(intent);
        });

        thumbsUp.setOnClickListener(onClick->{

            ContentValues cv=new ContentValues();
            cv.put("RATING","1");
            cv.put("ORDER_ID",order.orderID);
            req.doPostRequest(this,"rateOrder.php",cv,response ->{processResponse(response);});

        });

        thumbsDown.setOnClickListener(onClick->{
            ContentValues cv=new ContentValues();
            cv.put("RATING","0");
            cv.put("ORDER_ID",order.orderID);
            req.doPostRequest(this,"rateOrder.php",cv,response ->{processResponse(response);});

        });
    }

    public void processResponse(final String response)
    {

        if(!response.equals("Fail"))
        {
            Toast.makeText(this,"Thank you for rating!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Error occurred ",Toast.LENGTH_SHORT).show();
        }

    }

    public void processGetAvgRating(String respons, TextView avgRating) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(respons);
        JSONObject jsonObject=jsonArray.getJSONObject(0);
        String staffRating=jsonObject.getString("AVG(RATING)");

        if (!staffRating.equals("null"))
        {
            staffRating=jsonObject.getString("AVG(RATING)");
            Double staffRatingd=Double.parseDouble(staffRating)*100;
            avgRating.setText(String.valueOf(staffRatingd));

            ContentValues cv3=new ContentValues();
            cv3.put("STAFF_ID",order.empCode);
            cv3.put("AVG_RATING",Double.parseDouble(staffRating)*100);
            req.doPostRequest(this,"setAvgRating.php",cv3,response ->{});

        }
        else {
            avgRating.setText(staffRating);

            ContentValues cv3 = new ContentValues();
            cv3.put("STAFF_ID", order.empCode);
            cv3.put("AVG_RATING", staffRating);
            req.doPostRequest(this, "setAvgRating.php", cv3, response -> {});
        }

    }

    public void processStaffInfo(String response, TextView staffView) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        JSONObject jsonObject=jsonArray.getJSONObject(0);
        String fname=jsonObject.getString("F_NAME");
        String lname=jsonObject.getString("L_NAME");
        staffView.setText(fname+ " " +lname);

    }

}