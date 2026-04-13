package com.venusilkmills.app.Model;

public class DispachsubClas1 {
    public String SHADE;
    public String BOXNO;
    public String GRADE;
    public String LOTNO;


    public String getBOXNO() {
        return BOXNO;
    }

    public DispachsubClas1(String SHADE, String BOXNO) {
        this.SHADE = SHADE;
        this.BOXNO = BOXNO;
    }

    public DispachsubClas1(String BOXNO, String SHADE, String GRADE,String LOTNO) {
        this.BOXNO = BOXNO;
        this.SHADE = SHADE;
        this.GRADE = GRADE;
        this.LOTNO = LOTNO;
    }

    public void setBOXNO(String BOXNO) {
        this.BOXNO = BOXNO;
    }

    public DispachsubClas1(String SHADE) {
        this.SHADE = SHADE;
    }

    public String getSHADE() {
        return SHADE;
    }


}

