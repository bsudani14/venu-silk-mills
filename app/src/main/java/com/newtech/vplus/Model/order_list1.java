package com.newtech.vplus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class order_list1 {
    @SerializedName("Tcode")
String tcode;
    @SerializedName("Tparty")
    String tparty;
    @SerializedName("Tbrname")
    String tbrname;
    @SerializedName("Tsp")
    String tsp;
    @SerializedName("Tdate")
    String tdate;
    @SerializedName("TQty")
    String tqty;
    @SerializedName("Tcrday")
    String tcrday;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public String getTparty() {
        return tparty;
    }

    public void setTparty(String tparty) {
        this.tparty = tparty;
    }

    public String getTbrname() {
        return tbrname;
    }

    public void setTbrname(String tbrname) {
        this.tbrname = tbrname;
    }

    public String getTsp() {
        return tsp;
    }

    public void setTsp(String tsp) {
        this.tsp = tsp;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getTqty() {
        return tqty;
    }

    public void setTqty(String tqty) {
        this.tqty = tqty;
    }

    public String getTcrday() {
        return tcrday;
    }

    public void setTcrday(String tcrday) {
        this.tcrday = tcrday;
    }

    public String getTitem() {
        return titem;
    }

    public void setTitem(String titem) {
        this.titem = titem;
    }

    @SerializedName("TItem")
    String titem;

    public List<dispatch_detail> getOrder_item_list() {
        return order_item_list;
    }

    public void setOrder_item_list(List<dispatch_detail> order_item_list) {
        this.order_item_list = order_item_list;
    }

    @SerializedName("TItemlist")
    List<dispatch_detail> order_item_list;
}
