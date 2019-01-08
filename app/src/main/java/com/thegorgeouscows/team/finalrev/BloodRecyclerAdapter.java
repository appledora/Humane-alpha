package com.thegorgeouscows.team.finalrev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BloodRecyclerAdapter extends RecyclerView.Adapter<BloodRecyclerAdapter.ViewHolder> {
    public List<Blood> blood_list;
    public Context context;
    String userName,bloodType,phone;

    public BloodRecyclerAdapter(List<Blood>blood_list){
        this.blood_list = blood_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_list_item,parent,false);
        context = parent.getContext();
        return new BloodRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userName = blood_list.get(position).getBuserName();
        Log.i("my USERNAME: ",userName);

        bloodType = blood_list.get(position).getBbloodGroup();
        Log.i("my BLOOD: ",bloodType);
        phone = blood_list.get(position).getBcontact();


        holder.setBuserName(userName);
        holder.setBbloodGroup(bloodType);
        holder.setBcall(phone);

    }

    @Override
    public int getItemCount() {
        return blood_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Type;
        View view;
        Button callButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setBuserName(String userName) {
            Name = view.findViewById(R.id.blood_user_name);
            Name.setText(userName);
        }

        public void setBbloodGroup(String bloodType) {
            Type = view.findViewById(R.id.blood_group);
            Type.setText(bloodType);
        }

        public void setBcall(final String phone) {
            callButton = view.findViewById(R.id.call_blood);
            callButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+phone));
                    context.startActivity(callIntent);
                }
            });
        }
    }
}
