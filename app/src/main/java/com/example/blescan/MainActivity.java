package com.example.blescan;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    Button scan_btn;
    RecyclerView show_list;
    RxBleClient rxBleClient;
    Result_Adapter adapter;
    Disposable scanDisposable;
    boolean hasClickedScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        scan_btn = findViewById(R.id.start_btn);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScanning()) {
                    scanDisposable.dispose();
                } else {
                    if (rxBleClient.isScanRuntimePermissionGranted()) {
                        scanBLEDevices();
                    }
                    else {
                        hasClickedScan=true;
                        Location_Permission.requestLocationPermission(MainActivity.this,rxBleClient);
                    }
                }
            }
        });


        ButterKnife.bind(this);
        rxBleClient = SampleApplication.getRxBleClient(this);
        configResultlist();
    }

    private void scanBLEDevices() {
        scanDisposable = (Disposable) rxBleClient.scanBleDevices(new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setCallbackType(ScanSettings
                        .CALLBACK_TYPE_ALL_MATCHES).build(), new ScanFilter.Builder().build())
                .observeOn(AndroidSchedulers.mainThread()).doFinally(this::dispose)
                .subscribe(adapter::addScanResult, this::onScanFailure);
    }
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (Location_Permission.isRequestLocationPermissionGranted(requestCode, permissions, grantResults, rxBleClient)
                && hasClickedScan) {
            hasClickedScan = false;
            scanBLEDevices();
        }
    }

    public void onPause() {
        super.onPause();

        if (isScanning()) {
            /*
             * Stop scanning in onPause callback.
             */
            scanDisposable.dispose();
        }
    }
    private void configResultlist() {
        show_list.setHasFixedSize(true);
        show_list.setItemAnimator(null);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        show_list.setLayoutManager(manager);
        adapter = new Result_Adapter();
        show_list.setAdapter(adapter);
    }

    private boolean isScanning() {
        return scanDisposable != null;
    }
private void onScanFailure(Throwable throwable){
        if(throwable instanceof BleScanException){
            ScanExceptionHandler.handleException(this, (BleScanException) throwable);
        }
}
private void dispose(){
        scanDisposable=null;
        adapter.clearScanResult();
        updateUIButtonState();
}

    private void updateUIButtonState() {
        scan_btn.setText(isScanning() ? "Stop Scan":"Start Scan");
    }
}
