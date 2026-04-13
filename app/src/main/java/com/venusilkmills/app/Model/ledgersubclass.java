package com.venusilkmills.app.Model;

/**
 * Created by DELL on 15/03/2018.
 */


public class ledgersubclass {


    public String Rdate;
    public String Rled_account;
    public String RLed_ChqNo;
    public String Rled_vouno;

    public String getRdate() {
        return Rdate;
    }

    public void setRdate(String rdate) {
        Rdate = rdate;
    }

    public String getRled_account() {
        return Rled_account;
    }

    public void setRled_account(String rled_account) {
        Rled_account = rled_account;
    }

    public String getRLed_ChqNo() {
        return RLed_ChqNo;
    }

    public void setRLed_ChqNo(String RLed_ChqNo) {
        this.RLed_ChqNo = RLed_ChqNo;
    }

    public String getRled_vouno() {
        return Rled_vouno;
    }

    public void setRled_vouno(String rled_vouno) {
        Rled_vouno = rled_vouno;
    }

    public String getRDebit() {
        return RDebit;
    }

    public void setRDebit(String RDebit) {
        this.RDebit = RDebit;
    }

    public String getRCredit() {
        return RCredit;
    }

    public void setRCredit(String RCredit) {
        this.RCredit = RCredit;
    }

    public String getRled_amount() {
        return Rled_amount;
    }

    public void setRled_amount(String rled_amount) {
        Rled_amount = rled_amount;
    }

    public String RDebit;
    public String RCredit;
    public String Rled_amount;


}