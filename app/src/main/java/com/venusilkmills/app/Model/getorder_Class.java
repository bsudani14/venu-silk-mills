package com.venusilkmills.app.Model;

public class getorder_Class {
    public String pdm_shade;
    public String pdm_itcode;

    public getorder_Class(int quantity,String pdm_shade) {
        this.quantity = quantity;
        this.pdm_shade = pdm_shade;
    }

    public double quantity;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getPdm_shade() {
        return pdm_shade;
    }

    public void setPdm_shade(String pdm_shade) {
        this.pdm_shade = pdm_shade;
    }

    public String getPdm_itcode() {
        return pdm_itcode;
    }

    public void setPdm_itcode(String pdm_itcode) {
        this.pdm_itcode = pdm_itcode;
    }
}
