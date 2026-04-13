package com.newtech.vplus.Model;

public class StockClass {
    public String barcodeno;
    public String compcode;
    public String bookcode;
    public String rackno;
    public String godown;
    public String dbname;
    public String Type;

    public StockClass(String barcodeno, String compcode, String bookcode, String rackno, String godown, String dbname, String type) {
        this.barcodeno = barcodeno;
        this.compcode = compcode;
        this.bookcode = bookcode;
        this.rackno = rackno;
        this.godown = godown;
        this.dbname = dbname;
        Type = type;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String Result;

    public String getBarcodeno() {
        return barcodeno;
    }

    public void setBarcodeno(String barcodeno) {
        this.barcodeno = barcodeno;
    }

    public String getCompcode() {
        return compcode;
    }

    public void setCompcode(String compcode) {
        this.compcode = compcode;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public String getRackno() {
        return rackno;
    }

    public void setRackno(String rackno) {
        this.rackno = rackno;
    }

    public String getGodown() {
        return godown;
    }

    public void setGodown(String godown) {
        this.godown = godown;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    String NO;
}
