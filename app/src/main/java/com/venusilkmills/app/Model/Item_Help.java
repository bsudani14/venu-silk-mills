package com.venusilkmills.app.Model;


public class Item_Help {

    public String Itemnamec;
    public String shade;
    public String Qtyc;
    public String OrderBase;
    public Float t_rate;

    public String getDesigntype() {
        return designtype;
    }

    public void setDesigntype(String designtype) {
        this.designtype = designtype;
    }

    public String getShadename() {
        return shadename;
    }

    public void setShadename(String shadename) {
        this.shadename = shadename;
    }

    public String getSizetype() {
        return sizetype;
    }

    public void setSizetype(String sizetype) {
        this.sizetype = sizetype;
    }

    public String designtype;
    public String shadename;
    public String sizetype;

    public Item_Help(String itemnamec, String shade, String qtyc, String orderBase, Float t_rate,String designtype, String shadename, String sizetype) {
        Itemnamec = itemnamec;
        this.shade = shade;
        Qtyc = qtyc;
        OrderBase = orderBase;
        this.t_rate = t_rate;
        this.designtype = designtype;
        this.shadename = shadename;
        this.sizetype = sizetype;
    }

    public String getItemnamec() {
        return Itemnamec;
    }

    public void setItemnamec(String itemnamec) {
        Itemnamec = itemnamec;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public String getQtyc() {
        return Qtyc;
    }

    public void setQtyc(String qtyc) {
        Qtyc = qtyc;
    }

    public String getOrderBase() {
        return OrderBase;
    }

    public void setOrderBase(String orderBase) {
        OrderBase = orderBase;
    }

    public Float getT_rate() {
        return t_rate;
    }

    public void setT_rate(Float t_rate) {
        this.t_rate = t_rate;
    }

}

   /* public String And_id;
    Boolean status;
    @SerializedName("shade")
    public String i_code;
    @SerializedName("OrderBase")
    public String remark;
    @SerializedName("Itemnamec")
    String i_name;
    @SerializedName("Qtyc")
    int quantity;


    public Item_Help(String i_name, String i_code, int quantity, String remark) {
        // TODO Auto-generated constructor stub
        this.i_name = i_name;
        this.i_code = i_code;
        this.quantity = quantity;
        this.remark = remark;

    }


    public String get_iname() {
        return this.i_name;
    }

    public void set_iname(String i_name) {
        this.i_name = i_name;
    }

    public String get_icode() {
        return this.i_code;
    }

    public void set_icode(String i_code) {
        this.i_code = i_code;
    }

    public int get_quantity() {
        return this.quantity;
    }

    public void set_quantity(int quantity) {
        this.quantity = quantity;
    }


    public String get_remark() {
        return this.remark;
    }

    public void set_remark(String remark) {
        this.remark = remark;
    }
}



*/