package com.example.muslimart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//import com.google.firebase.auth.FirebaseAuth;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(welcome.this,firstPage.class);
                startActivity(intent);
                finish();

            }
        };
        handler.postDelayed(runnable,4000);

    }
}