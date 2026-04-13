package com.newtech.vplus.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class item_n {

    public item_n(String i_code, String i_name1) {
        this.i_code = i_code;
        this.i_name1 = i_name1;
    }
    @SerializedName("i_code")
    @Expose
    public String i_code;

    public String getI_code() {
        return i_code;
    }

    public void setI_code(String i_code) {
        this.i_code = i_code;
    }

    public String getI_name1() {
        return i_name1;
    }

    public void setI_name1(String i_name1) {
        this.i_name1 = i_name1;
    }
    @SerializedName("i_name1")
    @Expose
    public String i_name1;
}



