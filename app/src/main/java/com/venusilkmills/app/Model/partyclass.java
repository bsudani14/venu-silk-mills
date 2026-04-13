package com.venusilkmills.app.Model;

public class partyclass {


    public partyclass(String pname, String pcode) {
        this.pname = pname;
        this.pcode = pcode;
    }

    public partyclass() {

    }


    public String pname;
    public String pcode;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}


