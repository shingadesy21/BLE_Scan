package com.example.blescan;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;

import java.util.logging.Logger;

public class Scan_Result {
    private final RxBleClient bleDevice;
    private final int rss;
    private final long timestampnanos;
    private final ScanCallback callbacktype;
    private final ScanRecord scanRecord;

    public Scan_Result(RxBleClient bleDevice, int rss, long timestampnanos, ScanCallback callbacktype, ScanRecord scanRecord) {
        this.bleDevice = bleDevice;
        this.rss = rss;
        this.timestampnanos = timestampnanos;
        this.callbacktype = callbacktype;
        this.scanRecord = scanRecord;
    }

    public RxBleClient getBleDevice() {
        return bleDevice;
    }

    public int getRss() {
        return rss;
    }

    public long getTimestampnanos() {
        return timestampnanos;
    }

    public ScanCallback getCallbacktype() {
        return callbacktype;
    }

    public ScanRecord getScanRecord() {
        return scanRecord;
    }

    public String tostring(){
        return"Scan Result{"
                +"BLE Device="+bleDevice
                +", rss"+rss
                +", Time Stamp"+timestampnanos
                +", Callback Type"+callbacktype
                /*+", Scan Record" + LoggerUtil.bytesToHex(scanRecord.getBytes())+"}";*/
        +", Scan Records"+scanRecord+"}";
    }
}
