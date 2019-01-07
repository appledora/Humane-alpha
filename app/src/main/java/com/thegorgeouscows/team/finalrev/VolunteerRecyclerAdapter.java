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
        Log.i("my vol_id",vol_id);
        vol_address = volunteer_list.get(position).getVaddress();
        Log.i("my vol_address",vol_address);
        vol_contact = volunteer_list.get(position).getVcontact();
        Log.i("my vol_contact",vol_contact);

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
/*            mView.setOnClickListener(new View.OnClickListener() {
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
            });*/
        }

        public void setVname(String user_id) {
            nameText = mView.findViewById(R.id.vol_user_name);
            Log.i("my name:  ",user_id);
            nameText.setText(user_id);
        }

        public void setVaddress(String pickup_address) {
            addressText = mView.findViewById(R.id.vol_location);
            Log.i("my address: ",pickup_address);
            addressText.setText(pickup_address);
        }


        public void setVcontact(String vol_contact) {
            contactText = mView.findViewById(R.id.vol_contact_no);
            Log.i("my number: ",vol_contact);
            contactText.setText(vol_contact);
        }
    }
}
