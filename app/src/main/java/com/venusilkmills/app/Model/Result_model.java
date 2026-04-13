package com.venusilkmills.app.Model;

import com.google.gson.annotations.SerializedName;

public class Result_model{
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @SerializedName("Result")
    String result;

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    String NO;
}

