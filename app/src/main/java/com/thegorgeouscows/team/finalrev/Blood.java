package com.thegorgeouscows.team.finalrev;

public class Blood {

    String buserName,bbloodGroup,bcontact;
    public Blood(){

    }

    public Blood(String buserName, String bbloodGroup,String bcontact) {
        this.buserName = buserName;
        this.bbloodGroup = bbloodGroup;
        this.bcontact = bcontact;
    }

    public String getBcontact() {
        return bcontact;
    }

    public String getBuserName() {
        return buserName;
    }

    public void setBuserName(String buserName) {
        this.buserName = buserName;
    }

    public String getBbloodGroup() {
        return bbloodGroup;
    }

    public void setBbloodGroup(String bbloodGroup) {
        this.bbloodGroup = bbloodGroup;
    }
}
