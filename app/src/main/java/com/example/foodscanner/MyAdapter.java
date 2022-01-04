package com.example.foodscanner;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Product> products;
    Database database;

    Context context;
    boolean isButton;

    public MyAdapter(Context ct, List<Product> p, boolean isButton){
        context = ct;
        products = p;
        this.isButton = isButton;
        database = new Database(context);


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (!isButton) {
            view = inflater.inflate(R.layout.my_row, parent, false);
        } else {
            view = inflater.inflate(R.layout.my_row_favorite, parent, false);
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText("SUGAR: " + products.get(position).getSugar() + "g");
        holder.myText2.setText("CARBS: " + products.get(position).getCarbs() + "g");
        holder.myText3.setText("FAT: " + products.get(position).getFat() + "g");
        holder.myText4.setText("SALT: " + products.get(position).getSalt() + "g");
        holder.myText5.setText("ENERGY: " + products.get(position).getEnergy() + "kJ");
        holder.myText6.setText("SODIUM: " + products.get(position).getSodium() + "g");
        holder.myText7.setText("Protein: " + products.get(position).getProteins() + "g");

        //holder.myText8  HEALHINESSS
        holder.myText9.setText("NAME:" + products.get(position).getName());

        if (isButton) {
            int p = position;
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.button.getText().equals("delete")) {
                        database.removeProduct("Favorites", products.get(p));
//                        System.out.println("Deleting " + p);
                        Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show();

                    } else {
                        saveProduct(p);
                        Toast.makeText(context,"Added to Saved", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }


        Picasso.with(context).load(products.get(position).getImageUrl()).into(holder.myImage);

    }

    private void saveProduct(int pos) {
        database.addProductFav(products.get(pos));
    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1, myText2, myText3, myText4, myText5, myText6, myText7, myText8, myText9;
        ImageView myImage;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myText9 = itemView.findViewById(R.id.myText9);
            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
            myText3 = itemView.findViewById(R.id.myText3);
            myText4 = itemView.findViewById(R.id.myText4);
            myText5 = itemView.findViewById(R.id.myText5);
            myText6 = itemView.findViewById(R.id.myText6);
            myText7 = itemView.findViewById(R.id.myText7);
            // HEALHINESS myText8
            myImage = itemView.findViewById(R.id.imageView2);
            if (isButton) {
                button = itemView.findViewById(R.id.buttonSave);
                if (context.getClass().toString().equals(HomePage.class.toString())) {
                    button.setText("delete");
                    button.setTextSize(10);
                }
            }
        }
    }
}