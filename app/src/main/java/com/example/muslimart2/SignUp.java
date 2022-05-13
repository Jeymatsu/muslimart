package com.example.muslimart2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    FirebaseAuth myAuth;
    EditText edtEmail;
    EditText edtPassword;
    Button btnSignUp;
    TextView txtError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        txtError=findViewById(R.id.txtError);
        btnSignUp=findViewById(R.id.btnSignUp);
        myAuth=FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(v -> {
           createUser();

        });
    }
    public void createUser(){
        String email=edtEmail.getText().toString();
        String password=edtPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Email Cannot Be Empty");
            edtEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            edtPassword.setError("Password Cannnot Be Empty");
            edtPassword.requestFocus();
        }else{
            myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser fuser=myAuth.getCurrentUser();
                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SignUp.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {

                            }
                        });
                        Toast.makeText(SignUp.this, "User Registered Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, Login.class));
                    }else{
                        Toast.makeText(SignUp.this, "Registration Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}