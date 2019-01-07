package com.thegorgeouscows.team.finalrev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VolunteerRecyclerAdapter extends RecyclerView.Adapter<VolunteerRecyclerAdapter.ViewHolder> {

    public List<Volunteer> volunteer_list;
    public Context context;
    String vol_id,vol_address,vol_contact;

    public VolunteerRecyclerAdapter(List<Volunteer>volunteer_list){
        this.volunteer_list = volunteer_list;
    }
    @Override
    public VolunteerRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vol_list_items,parent,false);
        context = parent.getContext();
        return new VolunteerRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerRecyclerAdapter.ViewHolder holder, int position) {
        vol_id = volunteer_list.get(position).getVusername();
        vol_address = volunteer_list.get(position).getVaddress();
        vol_contact = volunteer_list.get(position).getVcontact();

        holder.setVname(vol_id);
        holder.setVaddress(vol_address);
        holder.setVcontact(vol_contact);
    }

    @Override
    public int getItemCount() {
       // Log.i("my SIZE: ","volunteer_list.size()");

        return volunteer_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nameText,addressText,contactText;
        private View mView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setVname(String user_id) {
            nameText = mView.findViewById(R.id.vol_user_name);
            nameText.setText(user_id);
        }

        public void setVaddress(String pickup_address) {
            addressText = mView.findViewById(R.id.vol_location);
            addressText.setText(pickup_address);
        }


        public void setVcontact(String vol_contact) {
            contactText = mView.findViewById(R.id.vol_contact_no);
            contactText.setText(vol_contact);
        }
    }
}
