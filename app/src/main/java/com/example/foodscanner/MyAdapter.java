package com.example.foodscanner;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<String> data1, data2, data3, data4, data5, data6, imageUrls;

    Context context;

    public MyAdapter(Context ct, List<String> s1, List<String> s2, List<String> s3, List<String> s4, List<String> s5, List<String> s6, List<String> urls){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        data6 = s6;
        imageUrls = urls;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1.get(position));
        holder.myText2.setText(data2.get(position));
        holder.myText3.setText(data3.get(position));
        holder.myText4.setText(data4.get(position));
        holder.myText5.setText(data5.get(position));
        holder.myText6.setText(data6.get(position));
        Picasso.with(context).load(imageUrls.get(position)).into(holder.myImage);

    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1, myText2, myText3, myText4, myText5, myText6;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
            myText3 = itemView.findViewById(R.id.myText3);
            myText4 = itemView.findViewById(R.id.myText4);
            myText5 = itemView.findViewById(R.id.myText5);
            myText6 = itemView.findViewById(R.id.myText6);
            myImage = itemView.findViewById(R.id.imageView2);
        }
    }
}
