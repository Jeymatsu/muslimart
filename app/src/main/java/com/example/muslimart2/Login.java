package com.example.muslimart2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    Button btnSignIn;
    FirebaseAuth myAuth;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    public static final Pattern USER_NAME = Pattern.compile("^([a-zA-Z])+([\\w]{2,})+$");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPassword=findViewById(R.id.edtPassword);
        edtEmail=findViewById(R.id.edtEmail);
        btnSignIn=findViewById(R.id.btnSignIn);
        myAuth=FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(v -> {
            loginUser();
        });

    }

    private void loginUser(){
        String email=edtEmail.getText().toString();
        String password=edtPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Email Cannot Be Empty");
            edtEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            edtPassword.setError("Password Cannnot Be Empty");
            edtPassword.requestFocus();
        }else {
            myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "User signed in Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,MainActivity.class));
                    }else{
                        Toast.makeText(Login.this, "sign in Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


}
