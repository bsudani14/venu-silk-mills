package com.venusilkmills.app.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.venusilkmills.app.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class qrscan_activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    TextView box;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        box=(TextView) findViewById(R.id.box);

        final int MY_CAMERA_REQUEST_CODE = 100;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(qrscan_activity.this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        } else {

            scannerView = new ZXingScannerView(qrscan_activity.this);

            setContentView(scannerView);
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
                if (checkPermission()) {
                    if (scannerView == null) {
                        scannerView = new ZXingScannerView(qrscan_activity.this);
                        setContentView(scannerView);
                    }
                    scannerView.setResultHandler(qrscan_activity.this);
                    scannerView.startCamera();


                } else {
                    //requestPermission();
                }
            }



        }
    }
    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                //requestPermission();
            }
        }
    }


 /*  @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }
*/
    @Override
    public void handleResult (Result result){
        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());
        box.setText(result.getText());
        scannerView.resumeCameraPreview(qrscan_activity.this);
        String str = box.getText().toString();
/*
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("value", str);
        editor.apply();*/


        //String qty = orderViewHolder.quantity.getText().toString();
        Intent intent = new Intent("custom-message");
        intent.putExtra("quantity",str);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        box.setText("");

        /*Intent intent = new Intent(getApplicationContext(), Dispatch_activity.class);
        intent.putExtra("scan", str);
        startActivity(intent);*/



    }
}
