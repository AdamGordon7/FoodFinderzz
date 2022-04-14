package com.example.restaurantv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class ChangeStatus extends AppCompatActivity {

    PHPrequests req=new PHPrequests("http://lamp.ms.wits.ac.za/home/s2275253/");
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_status);

        Intent i=getIntent();
        order= (Order) i.getSerializableExtra("Order");
        changeStatus();
    }

    public void changeStatus()
    {
        RadioButton pending=findViewById(R.id.bPending);
        RadioButton ready=findViewById(R.id.bReady);
        RadioButton collected=findViewById(R.id.bCollected);
        Button apply=findViewById(R.id.buttonApply);



        apply.setOnClickListener(onClick->{
            if(pending.isChecked())
            {
                ContentValues cv=new ContentValues();
                cv.put("ORDER_ID",order.orderID);
                cv.put("STATUS","PENDING");
                req.doPostRequest(this,"statusChange.php",cv,response -> {processResponse(response);});

            }
            if(ready.isChecked())
            {
                ContentValues cv=new ContentValues();
                cv.put("ORDER_ID",order.orderID);
                cv.put("STATUS","READY");
                req.doPostRequest(this,"statusChange.php",cv,response -> {processResponse(response);});
            }
            if(collected.isChecked())
            {
                ContentValues cv=new ContentValues();
                cv.put("ORDER_ID",order.orderID);
                cv.put("STATUS","COLLECTED");
                req.doPostRequest(this,"statusChange.php",cv,response -> {processResponse(response);});
            }
        });
    }

    public void processResponse(String response)
    {
        if(response.equals("Fail"))
        {
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Status Changed!",Toast.LENGTH_SHORT).show();
        }
    }

}