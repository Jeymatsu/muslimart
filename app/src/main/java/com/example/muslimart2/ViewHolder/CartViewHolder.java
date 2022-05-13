package com.example.muslimart2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muslimart2.Interface.ItemClickListener;
import com.example.muslimart2.R;

import org.jetbrains.annotations.NotNull;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice,txtQuantity;
    private ItemClickListener itemClickListener;
    public ImageView imgCartItem,imgCartDelete;

    public CartViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        txtProductName=itemView.findViewById(R.id.cart_ProductName);
        txtProductPrice=itemView.findViewById(R.id.cart_ProductPrice);
        txtQuantity=itemView.findViewById(R.id.cart_ProductQuantity);
        imgCartItem=itemView.findViewById(R.id.cart_ProductImage);
        imgCartDelete=itemView.findViewById(R.id.cart_ProductDelete);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onclick(view,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        imgCartDelete.setOnClickListener(this::onClick);
    }
}
