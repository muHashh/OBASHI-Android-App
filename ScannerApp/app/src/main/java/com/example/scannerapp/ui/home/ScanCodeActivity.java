package com.example.scannerapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scannerapp.R;
import com.example.scannerapp.ui.ar.ArActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final String TAG = "ScanCodeActivity";
    private static final int REQUEST_CODE = 1;
    ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

        verifyPermissions();
    }

    @Override
    public void handleResult(Result result){
        HomeFragment hf = HomeFragment.getInstance();
        hf.ll.setVisibility(View.VISIBLE);

        FetchDeviceAsyncTask connectMySql = new FetchDeviceAsyncTask(hf.name, hf.desc, hf.ll, hf.error);
        connectMySql.execute(result.getText());

        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume(){
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

    private void verifyPermissions() {
        Log.d(TAG, "Verifying permissions: asking user for camera permission");
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            onResume();
        }
        else {
            ActivityCompat.requestPermissions(ScanCodeActivity.this,
                    permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

}
