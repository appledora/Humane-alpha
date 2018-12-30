package com.thegorgeouscows.team.finalrev;

import android.util.Log;

import java.util.Date;

public class Clothes {
    private String  cpickupdate,cuserid,caddress,ccontact,cimage_url,cquantity,cprofilePhoto,cpickuptime;
    private Date ctimestamp;


    public Clothes(String cuserid, String cpickupdate, String caddress, String ccontact,String cpickuptime, String cimage_url, String cquantity,Date ctimestamp,String cprofilePhoto){
        this.cpickuptime = cpickuptime;
        this.cuserid = cuserid;
        Log.i("my","got User name from model: "+cuserid);
        this.cpickupdate = cpickupdate;
        Log.i("my","got  from model: "+cpickupdate);
        this.caddress = caddress;
        Log.i("my","got  from model: "+caddress);
        this.ccontact= ccontact;
        Log.i("my","got  from model: "+ccontact);
        this.cimage_url = cimage_url;
        Log.i("my","got from model: "+cimage_url);
        this.cquantity = cquantity;
        Log.i("my","got from model: "+cquantity);
        this.ctimestamp = ctimestamp;
        this.cprofilePhoto= cprofilePhoto;

    }

    public String getCpickuptime() {
        return cpickuptime;
    }

    public void setCpickuptime(String cpickuptime) {
        this.cpickuptime = cpickuptime;
    }

    public Clothes(){

    }

    public String getCpickupdate() {
        return cpickupdate;
    }

    public void setCpickupdate(String cpickupdate) {
        this.cpickupdate = cpickupdate;
    }

    public String getCuserid() {
        return cuserid;
    }

    public String getCimage_url() {
        return cimage_url;
    }

    public void setCimage_url(String cimage_url) {
        this.cimage_url = cimage_url;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    public String getCaddress() {
        return caddress;
    }

    public String getCimageurl() {
        return cimage_url;
    }

    public void setCimageurl(String cimage_url) {
        this.cimage_url = cimage_url;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;

    }

    public String getCcontact() {
        return ccontact;
    }

    public void setCcontact(String ccontact) {
        this.ccontact = ccontact;
    }


    public String getCquantity() {
        return cquantity;
    }

    public void setCquantity(String cquantity) {
        this.cquantity = cquantity;
    }

    public String getCprofilePhoto() {
        return cprofilePhoto;
    }

    public void setCprofilePhoto(String cprofilePhoto) {
        this.cprofilePhoto = cprofilePhoto;
    }

    public Date getCtimestamp() {
        return ctimestamp;
    }

    public void setCtimestamp(Date ctimestamp) {
        this.ctimestamp = ctimestamp;
    }

}
