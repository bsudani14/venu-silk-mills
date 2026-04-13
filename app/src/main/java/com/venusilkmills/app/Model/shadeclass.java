package com.venusilkmills.app.Model;

public class shadeclass {
    public String S_CODE,PK_SHADE,CL_QTY,PK_GRADEIS,ItemRate,Imgurl;
    int qdtqty;

    public shadeclass() {
    }

    public shadeclass(String S_CODE,String PK_SHADE, String CL_QTY, String PK_GRADEIS,int qdtqty,String Imgurl)
    {
        this.S_CODE = S_CODE;
        this.PK_SHADE = PK_SHADE;
        this.PK_GRADEIS = PK_GRADEIS;
        this.CL_QTY = CL_QTY;
        this.qdtqty = qdtqty;
        this.Imgurl=Imgurl;
    }


    public String getS_CODE() {
        return S_CODE;
    }

    public void setS_CODE(String sS_CODE) {
        this.S_CODE = sS_CODE;
    }

    public int getqdtqty() {
        return qdtqty;
    }

    public void setqdtqty(int sqdtqty) {
        this.qdtqty = sqdtqty;
    }

    public String getPK_SHADE() {
        return PK_SHADE;
    }

    public void setPK_SHADE(String sPK_SHADE) {
        this.PK_SHADE = sPK_SHADE;
    }

    public String getCL_QTY() {
        return CL_QTY;
    }

    public void setCL_QTY(String sCL_QTY) {
        this.CL_QTY = sCL_QTY;
    }

    public String getPK_GRADEIS() {
        return PK_GRADEIS;
    }

    public void setPK_GRADEIS(String sPK_GRADEIS) {
        this.PK_GRADEIS = sPK_GRADEIS;
    }

    public String getItemRate() {
        return ItemRate;
    }

    public void setItemRate(String ItemRate) {
        this.ItemRate = ItemRate;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String Imgurl) {
        this.Imgurl = Imgurl;
    }

    @Override
    public String toString() {
        String Json="{";
        Json= Json + "\""  + "S_CODE" + "\":"  + "\"" + S_CODE + "\",";
        Json= Json + "\""  + "PK_SHADE" + "\":"  + "\"" + PK_SHADE + "\",";
        Json= Json + "\""  + "CL_QTY" + "\":" + "\"" + CL_QTY + "\",";
        Json= Json + "\""  + "PK_GRADEIS" + "\":" + "\"" + PK_GRADEIS + "\",";
        Json= Json + "\""  + "Imgurl" + "\":" + "\"" + Imgurl + "\",";
        Json= Json + "\""  + "qdtqty" + "\":" + "\"" + qdtqty + "\"";
        Json= Json+ "}";
        return Json;
    }
}
