package com.venusilkmills.app.Model;

public class Khataclass {


    public String kh_code;
    public String kh_name;
    public boolean checked = false;


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getKh_code() {
        return kh_code;
    }

    public void setKh_code(String kh_code) {
        this.kh_code = kh_code;
    }

    public String getKh_name() {
        return kh_name;
    }

    public void setKh_name(String kh_name) {
        this.kh_name = kh_name;
    }

    public String getPermisssion() {
        return permisssion;
    }

    public void setPermisssion(String permisssion) {
        this.permisssion = permisssion;
    }

    public String permisssion;


}
