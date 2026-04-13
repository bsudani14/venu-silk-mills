package com.newtech.vplus.Model;

import com.google.gson.annotations.SerializedName;
public class party_data_end {
    @SerializedName("p_code")
    String pcode;

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @SerializedName("p_name")
    String pname;

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPmobile() {
        return pmobile;
    }

    public void setPmobile(String pmobile) {
        this.pmobile = pmobile;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    @SerializedName("p_address")
    String paddress;

    @SerializedName("p_mobile")
    String pmobile;

    @SerializedName("p_gst_in")
    String gstno;


    public String getP_brcode() {
        return p_brcode;
    }

    public void setP_brcode(String p_brcode) {
        this.p_brcode = p_brcode;
    }

    public String p_brcode;

}
