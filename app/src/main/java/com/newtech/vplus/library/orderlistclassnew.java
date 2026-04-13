package com.newtech.vplus.library;

public class orderlistclassnew {
	public String Rshade;
	 public String Rqty;
	 public String Rscode;
	
	

	    
	    public orderlistclassnew(){
	        super();
	    }
	    
	    public orderlistclassnew(String Rshade, String Rqty, String Rscode) {
	        super();
	       this.Rshade=Rshade;
	       this.Rqty=Rqty;
	       this.Rscode=Rscode;
	      
	   }
	    
	    @Override
		public String toString() {
			String Json="{";
			Json= Json + "\""  + "S_CODE" + "\":"  + "\"" + Rscode + "\",";
			Json= Json + "\""  + "PK_SHADE" + "\":"  + "\"" + Rshade + "\",";
			Json= Json + "\""  + "CL_QTY" + "\":" + "\"\",";
			Json= Json + "\""  + "PK_GRADEIS" + "\":" + "\"\",";
			Json= Json + "\""  + "Imgurl" + "\":" + "\"\",";
			Json= Json + "\""  + "qdtqty" + "\":" + "\"" + Rqty + "\"";
			Json= Json+ "}";	
			return Json;
		}	
	    
	    
}
