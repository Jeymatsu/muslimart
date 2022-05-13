package com.example.muslimart2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {


    ImageView kanzu;
    ImageView buibui;
    ImageView hijab;
    ImageView dress;
    ImageView mat;
    ImageView food;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        kanzu=findViewById(R.id.imgKanzu);
        buibui=findViewById(R.id.imgBuibui);
        hijab=findViewById(R.id.imgHijab);
        dress=findViewById(R.id.imgDress);
        mat=findViewById(R.id.imgMat);
        food=findViewById(R.id.imgFood);

        kanzu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Add_New_Product.class);
                intent.putExtra("category","KANZU");
                startActivity(intent);
            }
        });

        buibui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Add_New_Product.class);
                intent.putExtra("category","BUIBUI");
                startActivity(intent);

            }
        });
        hijab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Add_New_Product.class);
                intent.putExtra("category","HIJAB");
                startActivity(intent);
            }
        });
        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Add_New_Product.class);
                intent.putExtra("category","DRESS");
                startActivity(intent);
            }
        });
        mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Add_New_Product.class);
                intent.putExtra("category","MAT");
                startActivity(intent);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, Admin_Add_New_Product.class);
                intent.putExtra("category","FOOD");
                startActivity(intent);
            }
        });




    }
}