package com.thegorgeouscows.team.finalrev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodRecyclerAdapterDon extends RecyclerView.Adapter<FoodRecyclerAdapterDon.ViewHolder> {
    public List<Posts> posts_lists;
    public Context context;
    String user_id,pickup_address,contact_num, profile_photo,exp_date;


    public FoodRecyclerAdapterDon(List<Posts> posts_list) {
        this.posts_lists = posts_list;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_items_don, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
         user_id = posts_lists.get(position).getUserid();
         pickup_address = posts_lists.get(position).getAddress();
        String image_url = posts_lists.get(position).getImage_url();
         profile_photo = posts_lists.get(position).getProfilePhoto();
        long millisec = posts_lists.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisec)).toString();        //Log.i("my","recieved address: "+user_id);
        exp_date = posts_lists.get(position).getExpirationdate();
        contact_num = posts_lists.get(position).getContact();
        holder.setName(user_id);
        holder.setAddress(pickup_address);
        holder.setBlogImage(image_url);
        holder.setTime(dateString);
        holder.setProfilePhoto(profile_photo);
        holder.setExpiredate(exp_date);

    }

    @Override
    public int getItemCount() {
        return posts_lists.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView addressText, postDate,expDate;
        private View mView;
        private ImageView blogImage;
        private CircleImageView blogphoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context,PopUpActivity.class);
                    i.putExtra("username",user_id);
                    i.putExtra("location",pickup_address);
                    i.putExtra("contact_num",contact_num);
                    i.putExtra("id","Food");
                    i.putExtra("dp",profile_photo);
                    context.startActivity(i);
                }
            });
        }

        public void setName(String text) {

            addressText = mView.findViewById(R.id.blog_user_name);
            addressText.setText(text);
        }

        public void setAddress(String text) {

            addressText = mView.findViewById(R.id.location);
            addressText.setText(text);
        }

        public void setTime(String text) {

            postDate = mView.findViewById(R.id.blog_date);
            postDate.setText(text);
        }

        public void setBlogImage(String downloadUri) {
            blogImage = mView.findViewById(R.id.blog_image);
            Glide.with(context).load(downloadUri).into(blogImage);
        }


        public void setProfilePhoto(String profile_photo) {
            blogphoto = mView.findViewById(R.id.blog_user_image);
            Glide.with(context).load(profile_photo).into(blogphoto);
        }


        public void setExpiredate(String exp_date) {
            expDate = mView.findViewById(R.id.expire_date);
            expDate.setText(exp_date);

        }
    }
        public String getLocation(){
        return pickup_address;
        }
}
