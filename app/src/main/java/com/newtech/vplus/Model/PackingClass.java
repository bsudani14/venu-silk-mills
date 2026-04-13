package com.newtech.vplus.Model;

public class PackingClass {
 /*   public PackingClass(String PK_BoxNo, String PK_LOTNO, String PK_ITCODE, String PK_Grade, String PK_GrWt,String pk_netwt,String pk_cheese) {
        this.PK_BoxNo = PK_BoxNo;
        this.PK_LOTNO = PK_LOTNO;
        this.PK_ITCODE = PK_ITCODE;
        this.PK_Grade = PK_Grade;
        this.PK_GrWt = PK_GrWt;
        this.pk_netwt=pk_netwt;
        this.pk_cheese=pk_cheese;
    }*/

    public PackingClass(String pk_boxno, String pk_shade, String pk_itemcode, String pk_grade,String pk_lotno) {
        this.pk_boxno = pk_boxno;
        this.pk_shade = pk_shade;
        this.pk_itemcode = pk_itemcode;
        this.pk_grade = pk_grade;
        this.pk_lotno = pk_lotno;

    }

    public String pk_boxno;
    public String pk_shade;
    public String pk_itemcode;
    public String pk_grade;
    public String pk_lotno;
    public String pk_rackno;

    public String getPk_rackno() {
        return pk_rackno;
    }

    public void setPk_rackno(String pk_rackno) {
        this.pk_rackno = pk_rackno;
    }

    public String getPk_lotno() {
        return pk_lotno;
    }

    public void setPk_lotno(String pk_lotno) {
        this.pk_lotno = pk_lotno;
    }

    public String getPk_boxno() {
        return pk_boxno;
    }

    public void setPk_boxno(String pk_boxno) {
        this.pk_boxno = pk_boxno;
    }

    public String getPk_shade() {
        return pk_shade;
    }

    public void setPk_shade(String pk_shade) {
        this.pk_shade = pk_shade;
    }

    public String getPk_itemcode() {
        return pk_itemcode;
    }

    public void setPk_itemcode(String pk_itemcode) {
        this.pk_itemcode = pk_itemcode;
    }

    public String getPk_grade() {
        return pk_grade;
    }

    public void setPk_grade(String pk_grade) {
        this.pk_grade = pk_grade;
    }
}
