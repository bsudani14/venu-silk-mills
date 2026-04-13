package com.newtech.vplus.Model;

public class Stockpermission {
    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String Result;
    public String getA_code() {
        return A_code;
    }

    public void setA_code(String a_code) {
        A_code = a_code;
    }

    public String getA_name() {
        return A_name;
    }

    public void setA_name(String a_name) {
        A_name = a_name;
    }

    public String getA_costcenter() {
        return A_costcenter;
    }

    public void setA_costcenter(String a_costcenter) {
        A_costcenter = a_costcenter;
    }

    public String getA_permission() {
        return A_permission;
    }

    public void setA_permission(String a_permission) {
        A_permission = a_permission;
    }

    public String getA_date() {
        return A_date;
    }

    public void setA_date(String a_date) {
        A_date = a_date;
    }

    public Stockpermission(String a_code, String a_name, String a_costcenter, String a_permission, String a_date) {
        A_code = a_code;
        A_name = a_name;
        A_costcenter = a_costcenter;
        A_permission = a_permission;
        A_date = a_date;
    }

    public String A_code;
    public String A_name;
    public String A_costcenter;
    public String A_permission;
    public String A_date;

}
