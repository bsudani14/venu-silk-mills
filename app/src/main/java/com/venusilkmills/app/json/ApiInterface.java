package com.venusilkmills.app.json;


import com.venusilkmills.app.Model.BookClass;
import com.venusilkmills.app.Model.Closeorder;
import com.venusilkmills.app.Model.CompanylistClass;
import com.venusilkmills.app.Model.Dispachclass;
import com.venusilkmills.app.Model.Khataclass;
import com.venusilkmills.app.Model.LoginClass;
import com.venusilkmills.app.Model.MillProcess;
import com.venusilkmills.app.Model.OpeningBal;
import com.venusilkmills.app.Model.OrderschClass;
import com.venusilkmills.app.Model.OtpClass;
import com.venusilkmills.app.Model.PackingClass;
import com.venusilkmills.app.Model.Result_model;
import com.venusilkmills.app.Model.Salespersonclass;
import com.venusilkmills.app.Model.Staffmodel;
import com.venusilkmills.app.Model.StockClass;
import com.venusilkmills.app.Model.Stockpermission;
import com.venusilkmills.app.Model.addressmodel;
import com.venusilkmills.app.Model.designlist;
import com.venusilkmills.app.Model.getorder_Class;
import com.venusilkmills.app.Model.item_n;
import com.venusilkmills.app.Model.item_order;
import com.venusilkmills.app.Model.menu_list_model;
import com.venusilkmills.app.Model.order_Data;
import com.venusilkmills.app.Model.order_list;
import com.venusilkmills.app.Model.order_list1;
import com.venusilkmills.app.Model.orderno;
import com.venusilkmills.app.Model.dispatch_detail;
import com.venusilkmills.app.Model.orders_detail;
import com.venusilkmills.app.Model.party_data_end;
import com.venusilkmills.app.Model.shade_Data;
import com.venusilkmills.app.Model.shadelist;
import com.venusilkmills.app.Model.BrokerClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

   /* @GET("CMC/api/itemlist?")
    Call<List<item_n>> getitemlist(@Query("dbname") String apiKey);

    @GET("CMC/api/Designlist?")
    Call<List<designlist>> getdesignlist(@Query("dbname") String apiKey);

    @GET("CMC/api/Shadelist?")
    Call<List<shadelist>> getshadelist(@Query("dbname") String apiKey);


*/

    @GET("VPLUS/api/pendingorder?")
    Call<List<order_list>> getOrders(@Query("ordervalue") String radio, @Query("Ftype") String ftype, @Query("pcode") String pcode, @Query("dbname") String dbname, @Query("t_code") String t_code);

    @GET("VPLUS/api/Dispatch?")
    Call<List<order_list>> getDispatchOrderData(@Query("dbname") String dbname, @Query("chno") String chno, @Query("ftype") String ftype, @Query("code") String code);


    @GET("CMC/api/Dispatch?")
    Call<List<order_list1>> getDispatchOrderData1(@Query("dbname") String dbname, @Query("id") int id, @Query("pcode") String pcode, @Query("frdate") String frdate, @Query("todate") String todate);

    @GET("CMC/api/Dispatchsub?")
    Call<List<orders_detail>> getDispatchsubOrderData(@Query("dbname") String dbname, @Query("chalno") String chalno);

    @GET("MORAL/api/getmenuallocation?")
    Call<List<menu_list_model>> getUserWiseMenu(@Query("dbname") String dbname, @Query("empname") String empname, @Query("mobileno") String mobileno);

  /*  @GET("CMC/api/Salespersonlist?")
    Call<List<Salespersonclass>>getsalperson(@Query("dbname") String apiKey);

    @GET("CMC/api/Stockreportpermission?")
    Call<List<Khataclass>>getcostcenterpermission(@Query("dbname") String apiKey,@Query("code") String s_code);

    @POST("CMC/api/Stockpermissionadd")
    Call<Stockpermission> postpermisssion(@Header("Content-Type") String contentType, @Query("dbname") String apiKey, @Body ArrayList<Stockpermission> pa);*/

    @GET("CMC/api/Milprocess?")
    Call<List<MillProcess>> getprocessreport(@Query("p_Code")String pcode,@Query("dbname") String dbname);

    @POST("CMC/api/ApprovedConfirm?")
    Call<Closeorder> ApprovedConfirm(@Header("Content-Type") String contentType, @Query("dbname") String dbname, @Query("t_code") String tcode, @Query("ftype") String ftype);

    @POST("CMC/api/UNAPPROVED?")
    Call<Closeorder> UNApprovedConfirm(@Header("Content-Type") String contentType, @Query("dbname") String dbname, @Query("t_code") String tcode, @Query("ftype") String ftype,@Query("reason") String reason);

    @GET("CMC/api/AgentList?")
    Call<List<BrokerClass>>getbrokerlist(@Query("dbname") String dbname);





    @GET("VPLUS/api/Companylist?")
    Call<List<CompanylistClass>> getclist(@Query("dbname") String dbname);

    @GET("VPLUS/api/Khatalist?")
    Call<List<Khataclass>> getcost(@Query("dbname") String dbname,@Query("ftype") String ftype,@Query("code") String code);

    @GET("VPLUS/api/BookList?")
    Call<List<BookClass>> getbooklist(@Query("dbname") String dbname, @Query("cost") String cost, @Query("company") String company);

    @GET("VPLUS/api/Ordersch_List?")
    Call<List<OrderschClass>>getordersch(@Query("dbname") String dbname, @Query("cost") String cost, @Query("orderno") String compid,@Query("ordrackno") String ordrackno );

    @POST("VPLUS/api/AUTODISPACH?")
    Call<List<Result_model>>postdispach(@Header("Content-Type") String contentType, @Body Dispachclass pa);

  /*  @GET("VPLUS/api/Boxdetails?")
    Call<List<PackingClass>>getboxlist(@Query("dbname") String dbname, @Query("compid") String comid, @Query("shade") String shade, @Query("itmcode") String itmcode,@Query("rackno") String rackno,@Query("lotno") String lotno);
*/

    @POST("VPLUS/api/AddSchedule?")
    Call<orderno> addschedule(@Query("dbname") String dbname, @Query("scheduleno") String scheduleno);


    @GET("VPLUS/api/Boxdetails?")
    Call<List<PackingClass>>getboxlist(@Query("dbname") String dbname, @Query("compid") String comid, @Query("boxno") String boxno);

    @GET("VPLUS/api/OTP?")
    Call<List<LoginClass>> getlogindata(@Query("smobileno") String mobno, @Query("dbname") String dbname);

    @POST("VPLUS/api/otpverify?")
    Call<OtpClass> checkotp(@Header("Content-Type") String contentType, @Query("dbname") String apiKey, @Body OtpClass pa);

    @POST("VPLUS/api/BarcodeScane?")
    Call<StockClass> postbarcodedata(@Header("Content-Type") String contentType, @Body StockClass pa);

    @GET("VPLUS/api/party?")
    Call<List<party_data_end>> getpartylist(@Query("dbname") String dbname, @Query("type") String ptype, @Query("pname") String pname, @Query("brcode") String brcode);


    @GET("VPLUS/api/OpeningBal?")
    Call<List<OpeningBal>> getopenbal( @Query("pcode") String pcode,@Query("dbname") String dbname);

    @GET("VPLUS/api/ItemListUsingShadecard?")
    Call<List<order_Data>> getOrder(@Query("dbname") String dbname,@Query("shadecard") String shadecard);

    @GET("VPLUS/api/ShadeCardList?")
    Call<List<shade_Data>> getShade(@Query("dbname") String dbname);

    @GET("VPLUS/api/shadelist?")
    Call<List<getorder_Class>> getshadeList(@Query("dbname") String dbname, @Query("icode") String icode);



    @POST("MORAL/api/Menuallocation")
    Call<Result_model> setUserWiseMenu(@Header("Content-Type") String contentType, @Body menu_list_model menuListModel);

    @GET("MORAL/api/Staff?")
    Call<ArrayList<Staffmodel>> getUsers(@Query("dbname") String dbname,@Query("ftype")String ftype);

    @POST("VPLUS/api/partyorder")
    Call<List<Result_model>>getpartyorder(@Header("Content-Type") String contentType, @Query("dbname") String apiKey, @Body item_order pa);


    @GET("VPLUS/api/MultiBillAddress?")
    Call<List<addressmodel>>getAddress(@Query("dbname") String dbname, @Query("pname") String pname);
}

