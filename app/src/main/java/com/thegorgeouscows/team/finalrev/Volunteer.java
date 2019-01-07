package com.thegorgeouscows.team.finalrev;

public class Volunteer {

    String vusername,vaddress,vcontact,vemail;

    public Volunteer(String vusername,String vaddress,String vcontact,String vemail){
        this.vusername = vusername;
        this.vaddress = vaddress;
        this.vcontact = vcontact;
        this.vemail = vemail;
    }

    public Volunteer(){

    }

    public String getVusername() {
        return vusername;
    }

    public void setVusername(String vusername) {
        this.vusername = vusername;
    }

    public String getVaddress() {

        return vaddress;
    }

    public void setVaddress(String vaddress) {
        this.vaddress = vaddress;
    }

    public String getVcontact() {
        return vcontact;
    }

    public void setVcontact(String vcontact) {

        this.vcontact = vcontact;
    }

    public String getVemail() {
        return vemail;
    }

    public void setVemail(String vemail) {
        this.vemail = vemail;
    }
}
