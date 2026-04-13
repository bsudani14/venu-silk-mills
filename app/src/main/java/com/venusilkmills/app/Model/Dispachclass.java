package com.venusilkmills.app.Model;

import java.util.List;

public class Dispachclass {

    public String COMPANYNAME;
    public String BOOKNAME;
    public String COSTNAME;
    public String COSTID;
    public String SCHCODE;
    public String SCHPARTY;
    public String SCHQTY;

    public Dispachclass(String COMPANYNAME, String BOOKNAME, String COSTNAME, String COSTID, String SCHCODE, String SCHPARTY, String SCHQTY, String SCHBRNAME, String dbname, List<DispachsubClas> AP_LIST) {
        this.COMPANYNAME = COMPANYNAME;
        this.BOOKNAME = BOOKNAME;
        this.COSTNAME = COSTNAME;
        this.COSTID = COSTID;
        this.SCHCODE = SCHCODE;
        this.SCHPARTY = SCHPARTY;
        this.SCHQTY = SCHQTY;
        this.SCHBRNAME = SCHBRNAME;
        this.dbname = dbname;
        this.AP_LIST = AP_LIST;
    }

    public String SCHBRNAME;
    public String dbname;
    List<DispachsubClas> AP_LIST;

}

