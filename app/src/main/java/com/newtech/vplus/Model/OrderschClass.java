package com.newtech.vplus.Model;

public class OrderschClass {

    public OrderschClass(String osm_code, String osm_delparty, String osm_qty, String osm_brname, String osm_lotno, String osm_grade, String osm_itname, String osm_itcode, String stkqty, String rackno, String osm_ordno, String osm_SALQTY, String osm_TOTQTY, String shade) {
        this.osm_code = osm_code;
        this.osm_delparty = osm_delparty;
        this.osm_qty = osm_qty;
        this.osm_brname = osm_brname;
        this.osm_lotno = osm_lotno;
        this.osm_grade = osm_grade;
        this.osm_itname = osm_itname;
        this.osm_itcode = osm_itcode;
        this.stkqty = stkqty;
        this.rackno = rackno;
        this.osm_ordno = osm_ordno;
        this.osm_SALQTY = osm_SALQTY;
        this.osm_TOTQTY = osm_TOTQTY;
        this.shade = shade;
    }

    public String getOsm_code() {
        return osm_code;
    }

    public void setOsm_code(String osm_code) {
        this.osm_code = osm_code;
    }

    public String getOsm_date() {
        return osm_date;
    }

    public void setOsm_date(String osm_date) {
        this.osm_date = osm_date;
    }

    public String getOsm_delparty() {
        return osm_delparty;
    }

    public void setOsm_delparty(String osm_delparty) {
        this.osm_delparty = osm_delparty;
    }

    public String getOsm_qty() {
        return osm_qty;
    }

    public void setOsm_qty(String osm_qty) {
        this.osm_qty = osm_qty;
    }

    public String getOsm_brname() {
        return osm_brname;
    }

    public void setOsm_brname(String osm_brname) {
        this.osm_brname = osm_brname;
    }

    public String getOsm_lotno() {
        return osm_lotno;
    }

    public void setOsm_lotno(String osm_lotno) {
        this.osm_lotno = osm_lotno;
    }

    public String getOsm_grade() {
        return osm_grade;
    }

    public void setOsm_grade(String osm_grade) {
        this.osm_grade = osm_grade;
    }

    public String getOsm_itname() {
        return osm_itname;
    }

    public void setOsm_itname(String osm_itname) {
        this.osm_itname = osm_itname;
    }

    public String osm_code;
    public String osm_date;
    public String osm_delparty;
    public String osm_qty;
    public String osm_brname;
    public String osm_lotno;
    public String osm_grade;

    public String getDisqty() {
        return disqty;
    }

    public String setDisqty(String disqty) {
        this.disqty = disqty;
        return disqty;
    }

    public String osm_itname;
    public String osm_itcode;
    public String stkqty;
    public String disqty;

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String rackno;
    public String srno;


    public String getOsm_ordno() {
        return osm_ordno;
    }

    public void setOsm_ordno(String osm_ordno) {
        this.osm_ordno = osm_ordno;
    }

    public String osm_ordno;

    public String getOsm_itcode() {
        return osm_itcode;
    }

    public void setOsm_itcode(String osm_itcode) {
        this.osm_itcode = osm_itcode;
    }

    public String getStkqty() {
        return stkqty;
    }

    public void setStkqty(String stkqty) {
        this.stkqty = stkqty;
    }

    public String getRackno() {
        return rackno;
    }

    public void setRackno(String rackno) {
        this.rackno = rackno;
    }

    public String getOsm_SALQTY() {
        return osm_SALQTY;
    }

    public void setOsm_SALQTY(String osm_SALQTY) {
        this.osm_SALQTY = osm_SALQTY;
    }

    public String getOsm_TOTQTY() {
        return osm_TOTQTY;
    }

    public void setOsm_TOTQTY(String osm_TOTQTY) {
        this.osm_TOTQTY = osm_TOTQTY;
    }

    public String osm_SALQTY;
    public String osm_TOTQTY;
    public String shade;

    public boolean isCol() {
        return col;
    }

    public void setCol(boolean col) {
        this.col = col;
    }

    public boolean col=false;

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }
}


