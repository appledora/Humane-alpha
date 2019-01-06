package com.thegorgeouscows.team.finalrev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClothRecyclerAdapterOrg extends RecyclerView.Adapter<ClothRecyclerAdapterOrg.ViewHolder> {
    public List<Clothes>clothes_list;
    public Context context;
    String user_id,pickup_address,profile_photo,contact;


    public ClothRecyclerAdapterOrg(List<Clothes>clothes_list){
        Log.i("my","CLOTH RECYCLER ADAPTER");
        this.clothes_list = clothes_list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_list_items_org,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        user_id = clothes_list.get(position).getCuserid();
        pickup_address = clothes_list.get(position).getCaddress();
        String image_url = clothes_list.get(position).getCimage_url();
        profile_photo = clothes_list.get(position).getCprofilePhoto();
        contact = clothes_list.get(position).getCcontact();
        long millisec = clothes_list.get(position).getCtimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisec)).toString();

        holder.setCname(user_id);
        holder.setCaddress(pickup_address);
        holder.setCimage(image_url);
        holder.setCprofilePhoto(profile_photo);
        holder.setCdate(dateString);
        holder.setCallButton(contact);

    }



    @Override
    public int getItemCount() {
        return clothes_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView addressText,postDate,name;
        private View mView;
        private ImageView blogImage;
        private CircleImageView blogphoto;
        private Button callButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context,PopUpActivity.class);
                    i.putExtra("username",user_id);
                    i.putExtra("location",pickup_address);
                    i.putExtra("contact_num",contact);
                    i.putExtra("id","Food");
                    i.putExtra("dp",profile_photo);
                    context.startActivity(i);
                }
            });
        }

        public void setCname(String user_id) {
            name = mView.findViewById(R.id.cloth_user_name_org);
            name.setText(user_id);
        }

        public void setCaddress(String pickup_address) {
            addressText = mView.findViewById(R.id.cloth_location_org);
            addressText.setText(pickup_address);
        }

        public void setCprofilePhoto(String profile_photo) {
            blogphoto = mView.findViewById(R.id.cloth_user_image_org);
            Glide.with(context).load(profile_photo).into(blogphoto);
        }

        public void setCimage(String image_url) {
            blogImage = mView.findViewById(R.id.cloth_image_org);
            Glide.with(context).load(image_url).into(blogImage);
        }

        public void setCdate(String dateString) {
            postDate = mView.findViewById(R.id.cloth_date_org);
            postDate.setText(dateString);
        }

        public void setCallButton(final String contact) {
            Log.i("my setting callButton: ",contact);
            callButton = mView.findViewById(R.id.call_button_cloth_org);
            callButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+contact));
                    context.startActivity(callIntent);
                }
            });
        }
    }
}
