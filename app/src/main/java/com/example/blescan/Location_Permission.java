package com.example.blescan;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.polidea.rxandroidble2.RxBleClient;

public class Location_Permission {
    private Location_Permission(){ }
    private static final int REQUEST_PERMISSION_BLE_SCAN=9358;

    public static void requestLocationPermission(final Activity activity, final RxBleClient rxBleClient){
        ActivityCompat.requestPermissions(activity,new String[] {rxBleClient.getRecommendedScanRuntimePermissions()[0]},REQUEST_PERMISSION_BLE_SCAN);
    }
public static boolean isRequestLocationPermissionGranted(final int reqCode, final String[] permissions, final int[] grantResults, RxBleClient rxBleClient){
        if(reqCode!=REQUEST_PERMISSION_BLE_SCAN){
            return false;
        }
        String[] recommendedScanRuntimePermissions=rxBleClient.getRecommendedScanRuntimePermissions();
        for(int i=0;i<permissions.length;i++){
            for (String recommendedScanRuntimePermission : recommendedScanRuntimePermissions){
                if(permissions[i].equals(recommendedScanRuntimePermission) && grantResults[i]== PackageManager.PERMISSION_GRANTED){
                    return true;
                }
            }
        }
    return false;
}

}
