package com.newtech.vplus.Model;

import java.util.List;

/**
 * Created by DELL on 17/01/2018.
 */

public class item_order {

    public String Ordernoc;
    public String dbname;
    public String Datec;
    public String Partynamec;
    public String BrNamec;
    public String OrderBasedc;
    public String SalesPersonc;
    public String OrderTypec;
    public String CreditDaysc;
    public String Addressdc;
    public String Remarkc;
    public String type;
    public String dispstation;
    public String transporter;
    public String billing_name;
    public String T_Cut;

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }


    public String costcenter;
    public List<getorder_Class> Item;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String Result;

    public item_order(String ordernoc, String dbname, String datec, String partynamec, String brNamec, String orderBasedc, String salesPersonc, String orderTypec, String creditDaysc, String addressdc, String remarkc, String type, String dispstation, String transporter, String billing_name, String  t_Cut, String costcenter, List<getorder_Class> item) {
        Ordernoc = ordernoc;
        this.dbname = dbname;
        Datec = datec;
        Partynamec = partynamec;
        BrNamec = brNamec;
        OrderBasedc = orderBasedc;
        SalesPersonc = salesPersonc;
        OrderTypec = orderTypec;
        CreditDaysc = creditDaysc;
        Addressdc = addressdc;
        Remarkc = remarkc;
        this.type = type;
        this.dispstation = dispstation;
        this.transporter = transporter;
        this.billing_name = billing_name;
        T_Cut = t_Cut;
        this.costcenter = costcenter;
        Item = item;
    }

    public List<getorder_Class> getItem() {
        return Item;
    }

    public void setItem(List<getorder_Class> item) {
        Item = item;
    }

    public String getOrdernoc() {
        return Ordernoc;
    }

    public void setOrdernoc(String ordernoc) {
        Ordernoc = ordernoc;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDatec() {
        return Datec;
    }

    public void setDatec(String datec) {
        Datec = datec;
    }

    public String getPartynamec() {
        return Partynamec;
    }

    public void setPartynamec(String partynamec) {
        Partynamec = partynamec;
    }

    public String getBrNamec() {
        return BrNamec;
    }

    public void setBrNamec(String brNamec) {
        BrNamec = brNamec;
    }

    public String getOrderBasedc() {
        return OrderBasedc;
    }

    public void setOrderBasedc(String orderBasedc) {
        OrderBasedc = orderBasedc;
    }

    public String getSalesPersonc() {
        return SalesPersonc;
    }

    public void setSalesPersonc(String salesPersonc) {
        SalesPersonc = salesPersonc;
    }

    public String getOrderTypec() {
        return OrderTypec;
    }

    public void setOrderTypec(String orderTypec) {
        OrderTypec = orderTypec;
    }

    public String getCreditDaysc() {
        return CreditDaysc;
    }

    public void setCreditDaysc(String creditDaysc) {
        CreditDaysc = creditDaysc;
    }

    public String getAddressdc() {
        return Addressdc;
    }

    public void setAddressdc(String addressdc) {
        Addressdc = addressdc;
    }

    public String getRemarkc() {
        return Remarkc;
    }

    public void setRemarkc(String remarkc) {
        Remarkc = remarkc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDispstation() {
        return dispstation;
    }

    public void setDispstation(String dispstation) {
        this.dispstation = dispstation;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getBilling_name() {
        return billing_name;
    }

    public void setBilling_name(String billing_name) {
        this.billing_name = billing_name;
    }

    public String getT_Cut() {
        return T_Cut;
    }

    public void setT_Cut(String t_Cut) {
        T_Cut = t_Cut;
    }


}
