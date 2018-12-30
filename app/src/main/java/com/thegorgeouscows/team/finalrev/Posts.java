package com.thegorgeouscows.team.finalrev;

import android.util.Log;
import android.widget.ImageView;

import java.util.Date;

public class Posts {
    private String productiondate,expirationdate,userid,address,contact,imageurl,quantity,profilePhoto;
    private Date timestamp;


    public Posts(String user_name, String production_date, String expiration_date, String pickup_address, String contact, String image_url, String quantity,Date timestamp,String profilePhoto){
        this.userid = user_name;
        Log.i("my","got User name from model: "+user_name);
        this.productiondate = production_date;
        Log.i("my","got  from model: "+production_date);
        this.expirationdate = expiration_date;
        Log.i("my","got  from model: "+expiration_date);
        this.address = pickup_address;
        Log.i("my","got  from model: "+pickup_address);
        this.contact= contact;
        Log.i("my","got  from model: "+contact);
        this.imageurl = image_url;
        Log.i("my","got from model: "+image_url);
        this.quantity = quantity;
        Log.i("my","got from model: "+quantity);
        this.timestamp = timestamp;
        this.profilePhoto= profilePhoto;

    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Posts(){


    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage_url() {
        return imageurl;
    }

    public void setImage_url(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
