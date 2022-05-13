package com.example.muslimart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.muslimart2.Interface.ItemClickListener;
import com.example.muslimart2.Model.Products;
import com.example.muslimart2.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageSlider imageSlider;
    FloatingActionButton btnCart;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private DatabaseReference ProductsRef;
    private RecyclerView displayProducts,rcClothes;
    RecyclerView.LayoutManager layoutManager,layoutManagerClothes;
    ImageView imgDrawer;

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCart=findViewById(R.id.btnCart);

        displayProducts=findViewById(R.id.DisplayProducts);
        imageSlider=findViewById(R.id.slider);
        imgDrawer=findViewById(R.id.imgDrawer);
        //navigationView=findViewById(R.id.navigationView);
        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");
       // actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.close_menu);
       // drawerLayout.addDrawerListener(actionBarDrawerToggle);
       // actionBarDrawerToggle.syncState();
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        displayProducts.setHasFixedSize(true);
       layoutManager=new LinearLayoutManager(this);
       displayProducts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));


        List<SlideModel> slideModels= new ArrayList<>();
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/muslimart2-8c64f.appspot.com/o/Product%20Images%2F3000.png?alt=media&token=678111e9-212f-4fcd-8cac-58c556093702",null));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/muslimart2-8c64f.appspot.com/o/Product%20Images%2FMon_2_05_2022_01_20_36.png?alt=media&token=5bbb06d6-75c4-4510-9d9e-ba6d134e0541",null));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/muslimart2-8c64f.appspot.com/o/Product%20Images%2FMon_2_05_2022_01_22_55.png?alt=media&token=4f7191d9-d5b9-4046-8f6a-572e604c3dcd",null));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/muslimart2-8c64f.appspot.com/o/Product%20Images%2FMon_2_05_2022_01_25_38.png?alt=media&token=3edfbac8-d5db-4be7-bb55-628a9ebee39f",null));
        imageSlider.setImageList(slideModels,true);

        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });







     /*  navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()){
                    case R.id.navCart:
                        Toast.makeText(MainActivity.this, "Cart Is Clicked", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.navCategory:
                        Toast.makeText(MainActivity.this, "Category Clicked", Toast.LENGTH_SHORT).show();
                         drawerLayout.closeDrawer(GravityCompat.START);
                         break;
                         //CONTINUE WITH THE OTHERS
                }
                return true;
            }
        });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(ProductsRef,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter= new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull  Products model) {
                holder.txtProductName.setText(model.getProductname());
                //holder.txtProductDescription.setText(model.getProductdescription());
                holder.txtProductPrice.setText("Ksh "+model.getProductprice());
                Picasso.get().load(model.getImage()).into(holder.imgProductImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,ProductDetailsActivity.class);
                        intent.putExtra("pid",model.getPid());
                        startActivity(intent);
                    }
                });



            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item_layout,parent,false);
                ProductViewHolder holder= new ProductViewHolder(view);
                return holder;
            }

        };
        displayProducts.setAdapter(adapter);
        adapter.startListening();


    }
    public void getClothesDetails(){



    }

}


