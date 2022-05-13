package com.example.muslimart2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Admin_Add_New_Product extends AppCompatActivity {
    private TextView txtTitle;
    private String categoryName;
    private ImageView imgUpload;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private EditText edtStoreName,edtProductName,edtProductPrice,edtProductDescription;
    private String StoreName,ProductName,ProductPrice,ProductDescription;
    private Button btnAddNewProduct;
    private String saveCurrentDate,saveCurrentTime;
    private String productRandomKey;
    private StorageReference productImagesRef;
    private String downloadImageUrl;
    private DatabaseReference productsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        txtTitle=findViewById(R.id.txtTitle);
        btnAddNewProduct=findViewById(R.id.btnAddNewProduct);
        edtStoreName=findViewById(R.id.edtStoreName);
        edtProductName=findViewById(R.id.edtProductName);
        edtProductPrice=findViewById(R.id.edtProductPrice);
        edtProductDescription=findViewById(R.id.edtProductDescription);
        imgUpload=findViewById(R.id.imgUpload);

        productImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products");



        categoryName=getIntent().getExtras().get("category").toString();
        txtTitle.setText("ADD "+categoryName);

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });

    }

    public void openGallery(){
        /*Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);*/
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GalleryPick);//image uri



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==GalleryPick&&resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            imgUpload.setImageURI(ImageUri);*/
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            ImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                imgUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    public void validateProductData(){
        StoreName=edtStoreName.getText().toString();
        ProductName=edtProductName.getText().toString();
        ProductPrice=edtProductPrice.getText().toString();
        ProductDescription=edtProductDescription.getText().toString();

        if(ImageUri==null){
            Toast.makeText(this, "PlEASE UPLOAD IMAGE", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(StoreName)){
           edtStoreName.setError("Store Name Cannot be Empty");
           edtStoreName.requestFocus();
        }else if(TextUtils.isEmpty(ProductName)){
            edtProductName.setError("Product Name Cannot be Empty");
            edtProductName.requestFocus();
        }else if(TextUtils.isEmpty(ProductPrice)){
            edtProductPrice.setError("Product Price Cannot be Empty");
            edtProductPrice.requestFocus();
        }else if(TextUtils.isEmpty(ProductDescription)){
            edtProductDescription.setError("Product Description Cannot be Empty");
            edtProductDescription.requestFocus();
        }else{
            storeProductInformation();

        }

    }


    public void storeProductInformation(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM,dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:MM:SS a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate + saveCurrentTime;

        StorageReference filepath=productImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+".jpg");
        final UploadTask uploadTask=filepath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                String message=e.toString();
                Toast.makeText(Admin_Add_New_Product.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Admin_Add_New_Product.this, "IMAGE UPLOADED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then( Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete( Task<Uri> task) {
                        downloadImageUrl=task.getResult().toString();
                        addProductToDatabase();
                    }
                });
            }
        });


    }
    public void addProductToDatabase(){
        StoreName=edtStoreName.getText().toString();
        ProductName=edtProductName.getText().toString();
        ProductPrice=edtProductPrice.getText().toString();
        ProductDescription=edtProductDescription.getText().toString();

        HashMap<String,Object> productMap= new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("category",categoryName);
        productMap.put("storename",StoreName);
        productMap.put("productname",ProductName);
        productMap.put("productprice",ProductPrice);
        productMap.put("productdescription",ProductDescription);
        productMap.put("image",downloadImageUrl);

        productsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Admin_Add_New_Product.this, "PRODUCT ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(Admin_Add_New_Product.this,MainActivity.class);
                    startActivity(intent);
                } else{
                    String message=task.getException().toString();
                    Toast.makeText(Admin_Add_New_Product.this, "ERROR: "+message, Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
}