package com.newtech.vplus.Model;

public class orderlistclass {
	private String ono;
	private String odt;
	private String opname;
	private String oqty;
	private String obrname;
	private String ocr;
	private String osp;
	private String oitem;

	public String getT_LOCKTERMS() {
		return T_LOCKTERMS;
	}

	public void setT_LOCKTERMS(String t_LOCKTERMS) {
		T_LOCKTERMS = t_LOCKTERMS;
	}

	private String T_LOCKTERMS;

	public String getOreason() {
		return oreason;
	}

	public void setOreason(String oreason) {
		this.oreason = oreason;
	}

	private String oreason ;

	public orderlistclass() {
	}

	public orderlistclass(String ono, String odt, String opname, String oqty, String obrname, String ocr, String osp,String oitem) {
		this.ono = ono;
		this.odt = odt;
		this.opname = opname;
		this.oqty = oqty;
		this.obrname = obrname;
		this.ocr = ocr;
		this.osp = osp;
		this.oitem = oitem;
	}

	public String getono() {
		return ono;
	}

	public void setono(String sono) {
		this.ono = sono;
	}

	public String getodt() {
		return odt;
	}

	public void setodt(String sodt) {
		this.odt = sodt;
	}

	public String getopname() {
		return opname;
	}

	public void setopnamet(String sopname) {
		this.opname = sopname;
	}

	public String getobrname() {
		return obrname;
	}

	public void setobrname(String sobrname) {
		this.obrname = sobrname;
	}
	
	public String getocr() {
		return ocr;
	}

	public void setocr(String socr) {
		this.ocr = socr;
	}
	
	public String getoqty() {
		return oqty;
	}

	public void setoqty(String soqty) {
		this.oqty = soqty;
	}
	
	public String getosp() {
		return osp;
	}

	public void setosp(String sosp) {
		this.osp = sosp;
	}
	
	public String getoitem() {
		return oitem;
	}

	public void setoitem(String soitem) {
		this.oitem = soitem;
	}

}
