package com.venusilkmills.app.Model;

public class addressmodel {
    String address;
    public String psrl;

    public String getPsrl() {
        return psrl;
    }

    public void setPsrl(String psrl) {
        this.psrl = psrl;
    }

    public addressmodel(String address,String psrl) {
        this.address = address;
        this.psrl = psrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return getAddress(); // You can add anything else like maybe getDrinkType()
    }
}
