package com.example.muslimart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShippingDetails extends AppCompatActivity {
    private  String shiptotal;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);
        txtTotal=findViewById(R.id.txtTotal);
        shiptotal=getIntent().getExtras().get("total").toString();
        txtTotal.setText("ksh "+ shiptotal);


    }
    public void openPayment(View view){
        Intent intent = new Intent(ShippingDetails.this,PaymentMethod.class);
        startActivity(intent);
    }
}