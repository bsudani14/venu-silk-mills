package com.venusilkmills.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.icu.text.NumberFormat;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.venusilkmills.app.R;

import java.text.Format;
import java.util.Locale;

public class BottomTotalShow extends BottomSheetDialogFragment {

    private String TotalAmount,CrAmount,DrAmount;
    private TextView totalCreditAmount,totalDebitAmount,totalFinalAmount;
    private LinearLayout linearCr,linearDr,linearFa;

    public BottomTotalShow(){
        // EMPTY CONSTRUC
    }

    public BottomTotalShow(String totalAmount,String totalCR,String totalDB){
        this.TotalAmount = totalAmount;
        this.CrAmount = totalCR;
        this.DrAmount = totalDB;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        final View contentView = View.inflate(getContext(), R.layout.bottom_total_show_layout,null);
        dialog.setContentView(contentView);

        totalCreditAmount = (TextView) contentView.findViewById(R.id.totalCreditAmount);
        totalDebitAmount = (TextView) contentView.findViewById(R.id.totalDebitAmount);
        totalFinalAmount = (TextView) contentView.findViewById(R.id.totalFinalAmount);

        linearCr = (LinearLayout) contentView.findViewById(R.id.linearCr);
        linearDr = (LinearLayout) contentView.findViewById(R.id.linearDr);
        linearFa = (LinearLayout) contentView.findViewById(R.id.linearFa);

        Format format = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        }

        if(CrAmount != null && DrAmount !=null){
            totalCreditAmount.setText("+ "+String.valueOf(format.format(new Double(CrAmount))));
            totalDebitAmount.setText("- "+String.valueOf(format.format(new Double(DrAmount))));
        }else{
            linearCr.setVisibility(View.GONE);
            totalCreditAmount.setVisibility(View.GONE);
            linearDr.setVisibility(View.GONE);
            totalDebitAmount.setVisibility(View.GONE);
        }

        totalFinalAmount.setText("= "+String.valueOf(format.format(new Double(TotalAmount))));

        System.out.println(format.format(new Integer(10000)));

    }

}
