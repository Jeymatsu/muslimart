package com.example.muslimart2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class firstPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(firstPage.this, Login.class);
        startActivity(intent);

    }
    public void openSignUpActivity(View view){

        Intent intent=new Intent(firstPage.this,SignUp.class);
        startActivity(intent);
    }
    public void Continue(View view){
        Intent intent=new Intent(firstPage.this,Admin.class);
        startActivity(intent);

    }




}