package com.venusilkmills.app.Model;

public class OtpClass {

    public String otpno;

    public OtpClass(String otpno, String smobileno) {
        this.otpno = otpno;
        this.smobileno = smobileno;
    }

    public String smobileno;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String Result;

    public String getOtpno() {
        return otpno;
    }

    public void setOtpno(String otpno) {
        this.otpno = otpno;
    }

    public String getSmobileno() {
        return smobileno;
    }

    public void setSmobileno(String smobileno) {
        this.smobileno = smobileno;
    }


}
