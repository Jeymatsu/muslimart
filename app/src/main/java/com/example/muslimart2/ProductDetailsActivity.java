package com.example.muslimart2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.muslimart2.Model.Cart;
import com.example.muslimart2.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView productsImage;
    private TextView txtProductName;
    FirebaseUser user;
    String Email;
    private TextView txtProductDesription;
    private TextView txtProductPrice;
    private ElegantNumberButton numberButton;
    private String ProductID="";
    private String productImageURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ProductID=getIntent().getStringExtra("pid");
        user=FirebaseAuth.getInstance().getCurrentUser();
        Email=user.getUid();

        productsImage=findViewById(R.id.product_image_details);
        txtProductName=findViewById(R.id.product_Name_details);
        txtProductDesription=findViewById(R.id.product_Description_details);
        txtProductPrice=findViewById(R.id.product_price_details);
        numberButton=findViewById(R.id.Number_details);
        getProductDetails(ProductID);


    }

    private void getProductDetails(String productID) {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Products products=snapshot.getValue(Products.class);
                    txtProductName.setText(products.getProductname());
                    txtProductPrice.setText(products.getProductprice());
                    txtProductDesription.setText(products.getProductdescription());
                    Picasso.get().load(products.getImage()).into(productsImage);
                    productImageURL=products.getImage();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void addCart(View view){
        addingtoCartList();
    }


    private void addingtoCartList() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat curentDate= new SimpleDateFormat("MMM:DD:yyyy");
        saveCurrentDate=curentDate.format(calForDate.getTime());

        SimpleDateFormat curentTime= new SimpleDateFormat("HH:MM:SS");
        saveCurrentTime=curentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pid",ProductID);
        cartMap.put("pimage",productImageURL);
        cartMap.put("pname",txtProductName.getText().toString());
        cartMap.put("price",txtProductPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount",null);

        cartListRef.child("user view"). child(ProductID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartListRef.child("admin view"). child(ProductID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Toast.makeText(ProductDetailsActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(ProductDetailsActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });/*child(prevalent.currentOnlineUser.getPhone())*/


    }
}