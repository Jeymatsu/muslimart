package com.example.muslimart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.muslimart2.Model.Cart;
import com.example.muslimart2.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CartActivity extends AppCompatActivity {
    TextView txtTotalPrice;
    RecyclerView recyclerView;
    public  String total;
    RecyclerView.LayoutManager layoutManager;
    int totalAmount=0;
    RelativeLayout rlCheckout;
    TextView txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.cartList);
        txtTotal=findViewById(R.id.txtTotalAmount);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("user view"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter= new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CartViewHolder holder, int position, @NonNull @NotNull Cart model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Ksh "+model.getPrice());
                holder.txtQuantity.setText(model.getQuantity() +" Pieces");
                Picasso.get().load(model.getPimage()).into(holder.imgCartItem);
                int productPrice=((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                totalAmount=totalAmount+productPrice;
                txtTotal.setText("ksh "+String.valueOf(totalAmount));

                holder.imgCartDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cartListRef.child("user view").child(model.getPid()).removeValue();
                        int productPrice=((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                        totalAmount=totalAmount-productPrice;
                        txtTotal.setText("ksh "+String.valueOf(totalAmount));
                        total= (String) txtTotal.getText();

                    }
                });



            }

            @NonNull
            @NotNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
                CartViewHolder holder= new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    public void openShipping(View view){
        Intent intent=new Intent(CartActivity.this, ShippingDetails.class);
        startActivity(intent);
    }


}