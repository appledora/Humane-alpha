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

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder> {
    public List<Posts> posts_lists;

    public FoodRecyclerAdapter(List<Posts> posts_list) {
        this.posts_lists = posts_list;
    }

    public Context context;
    String user_id,pickup_address,contact_numb, profile_photo,expire_date;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_items, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.i("my","ViewHolder Binding");
         user_id = posts_lists.get(position).getUserid();
         pickup_address = posts_lists.get(position).getAddress();
        String image_url = posts_lists.get(position).getImage_url();
         contact_numb = posts_lists.get(position).getContact();
        profile_photo = posts_lists.get(position).getProfilePhoto();
        expire_date = posts_lists.get(position).getExpirationdate();


        long millisec = posts_lists.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisec)).toString();        //Log.i("my","recieved address: "+user_id);
        holder.setName(user_id);
        holder.setAddress(pickup_address);
        holder.setBlogImage(image_url);
        holder.setTime(dateString);
        holder.setProfilePhoto(profile_photo);
        holder.setCallButton(contact_numb);
        holder.setExpireDate(expire_date);
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
                    i.putExtra("contact_num",contact_numb);
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

       public void setCallButton(final String contact_numb) {
            Log.i("my setting callButton: ",contact_numb);
            callButton = mView.findViewById(R.id.call_button);
            callButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+contact_numb));
                    context.startActivity(callIntent);
                }
            });

        }

        public void setExpireDate(String expire_date) {
            expDate = mView.findViewById(R.id.expiration_date);
            expDate.setText(expire_date);
        }
    }
}
