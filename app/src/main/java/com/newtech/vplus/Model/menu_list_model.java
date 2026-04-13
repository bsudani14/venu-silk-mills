package com.newtech.vplus.Model;

public class menu_list_model {
    public String A_EMPNAME;
    public String A_MOBILENO;
    private boolean A_LEDGERBILL;
    private boolean A_ORDER;
    public boolean A_ORDERDETAILS;
    public boolean A_CHECKINGMODULE;
    public boolean A_CHECKINGDETAILS;
    private boolean A_AVGBOOK;

    private boolean A_MENUALLOCATION;
    private String dbname;

    public String getA_EMPNAME() {
        return A_EMPNAME;
    }

    public void setA_EMPNAME(String a_EMPNAME) {
        A_EMPNAME = a_EMPNAME;
    }

    public String getA_MOBILENO() {
        return A_MOBILENO;
    }

    public void setA_MOBILENO(String a_MOBILENO) {
        A_MOBILENO = a_MOBILENO;
    }

    public boolean isA_LEDGERBILL() {
        return A_LEDGERBILL;
    }

    public void setA_LEDGERBILL(boolean a_LEDGERBILL) {
        A_LEDGERBILL = a_LEDGERBILL;
    }

    public boolean isA_ORDER() {
        return A_ORDER;
    }

    public void setA_ORDER(boolean a_ORDER) {
        A_ORDER = a_ORDER;
    }

    public boolean isA_ORDERDETAILS() {
        return A_ORDERDETAILS;
    }

    public void setA_ORDERDETAILS(boolean a_ORDERDETAILS) {
        A_ORDERDETAILS = a_ORDERDETAILS;
    }

    public boolean isA_CHECKINGMODULE() {
        return A_CHECKINGMODULE;
    }

    public void setA_CHECKINGMODULE(boolean a_CHECKINGMODULE) {
        A_CHECKINGMODULE = a_CHECKINGMODULE;
    }

    public boolean isA_CHECKINGDETAILS() {
        return A_CHECKINGDETAILS;
    }

    public void setA_CHECKINGDETAILS(boolean a_CHECKINGDETAILS) {
        A_CHECKINGDETAILS = a_CHECKINGDETAILS;
    }

    public boolean isA_AVGBOOK() {
        return A_AVGBOOK;
    }

    public void setA_AVGBOOK(boolean a_AVGBOOK) {
        A_AVGBOOK = a_AVGBOOK;
    }

    public boolean isA_MENUALLOCATION() {
        return A_MENUALLOCATION;
    }

    public void setA_MENUALLOCATION(boolean a_MENUALLOCATION) {
        A_MENUALLOCATION = a_MENUALLOCATION;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
}
