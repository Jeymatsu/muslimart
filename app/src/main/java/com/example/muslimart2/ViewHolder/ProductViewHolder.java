package com.example.muslimart2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muslimart2.Interface.ItemClickListener;
import com.example.muslimart2.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDescription,txtProductPrice;
    public ImageView imgProductImage;
    public ItemClickListener itemClickListener;
    public ProductViewHolder( View itemView) {
        super(itemView);
        imgProductImage=(ImageView)itemView.findViewById(R.id.product_image);
        //txtProductDescription=(TextView)itemView.findViewById(R.id.product_description);
        txtProductName=(TextView)itemView.findViewById(R.id.product_Name);
        txtProductPrice=(TextView)itemView.findViewById(R.id.product_price);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onclick(view,getAdapterPosition(),false);

    }
}
