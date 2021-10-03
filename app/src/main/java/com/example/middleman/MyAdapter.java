package com.example.middleman;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final Integer MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private ArrayList<Property> mList;
    private Context context;

    public MyAdapter(Context context,ArrayList<Property> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rc_property_price_textview.setText("₹ "+mList.get(position).property_price);
        holder.rc_property_address_textview.setText(mList.get(position).property_address);
        holder.rc_property_name_textview.setText(mList.get(position).property_name);
        holder.rc_property_phone_number_textview.setText(mList.get(position).property_phone_number);
        Picasso.with(context).load(mList.get(position).upload_front_image_uri).into(holder.rc_property_front_main_image_imageview);


        holder.material_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,Detail.class);
                i.putExtra("latitude", mList.get(holder.getAdapterPosition()).latitude.toString());
                i.putExtra("longitude",mList.get(holder.getAdapterPosition()).longitude.toString());
                i.putExtra("property_address",mList.get(holder.getAdapterPosition()).property_address);
                i.putExtra("property_area",mList.get(holder.getAdapterPosition()).property_area);
                i.putExtra("property_BHK",mList.get(holder.getAdapterPosition()).property_BHK);
                i.putExtra("property_email_id",mList.get(holder.getAdapterPosition()).property_email_id);
                i.putExtra("property_locality",mList.get(holder.getAdapterPosition()).property_locality);
                i.putExtra("property_name",mList.get(holder.getAdapterPosition()).property_name);
                i.putExtra("property_owner_name",mList.get(holder.getAdapterPosition()).property_owner_name);
                i.putExtra("property_phone_number",mList.get(holder.getAdapterPosition()).property_phone_number);
                i.putExtra("property_price",mList.get(holder.getAdapterPosition()).property_price);
                i.putExtra("upload_first_image_uri",mList.get(holder.getAdapterPosition()).upload_first_image_uri);
                i.putExtra("upload_fourth_image_url",mList.get(holder.getAdapterPosition()).upload_fourth_image_uri);
                i.putExtra("upload_second_image_url",mList.get(holder.getAdapterPosition()).upload_second_image_uri);
                i.putExtra("upload_front_image_url",mList.get(holder.getAdapterPosition()).upload_front_image_uri);
                i.putExtra("upload_third_image_url",mList.get(holder.getAdapterPosition()).upload_third_image_uri);
                i.putExtra("userID",mList.get(holder.getAdapterPosition()).userID);
                context.startActivity(i);
            }
        });
        holder.rc_property_phone_number_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+holder.rc_property_phone_number_textview.getText().toString()));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView rc_property_front_main_image_imageview;
        TextView rc_property_name_textview,rc_property_price_textview,rc_property_phone_number_textview,rc_property_address_textview;
        MaterialCardView material_card_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            material_card_view = itemView.findViewById(R.id.material_card_view);
            rc_property_front_main_image_imageview = itemView.findViewById(R.id.rc_property_front_main_image_imageview);
            rc_property_name_textview = itemView.findViewById(R.id.rc_property_name_textview);
            rc_property_address_textview = itemView.findViewById(R.id.rc_property_address_textview);
            rc_property_price_textview = itemView.findViewById(R.id.rc_property_price_textview);
            rc_property_phone_number_textview = itemView.findViewById(R.id.rc_property_phone_number_textview);
        }
    }
}
