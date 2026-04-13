package com.venusilkmills.app.Model;

public class DispachsubClas {


    public String BOXNO;
    public String SHADE;

    public String getLOTNO() {
        return LOTNO;
    }

    public void setLOTNO(String LOTNO) {
        this.LOTNO = LOTNO;
    }

    public String GRADE;
    public String LOTNO;

    public String getBOXNO() {
        return BOXNO;
    }

    public DispachsubClas(String BOXNO, String SHADE, String GRADE,String LOTNO) {
        this.BOXNO = BOXNO;
        this.SHADE = SHADE;
        this.GRADE = GRADE;
        this.LOTNO = LOTNO;
    }

    public void setBOXNO(String BOXNO) {
        this.BOXNO = BOXNO;
    }

    public String getSHADE() {
        return SHADE;
    }

    public void setSHADE(String SHADE) {
        this.SHADE = SHADE;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }
}
