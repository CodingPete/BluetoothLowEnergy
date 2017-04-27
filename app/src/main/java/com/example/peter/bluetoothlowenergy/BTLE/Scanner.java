package com.example.peter.bluetoothlowenergy.BTLE;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.*;
import android.content.ContentResolver;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.example.peter.bluetoothlowenergy.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by peter on 16.04.2017.
 */
public class Scanner {

    private static ContentResolver contentResolver;
    private static String TAG = "Scanner";

    public Scanner(Context context, BluetoothAdapter btAdapter) {
        contentResolver = context.getContentResolver();
        BluetoothLeScanner btScanner = btAdapter.getBluetoothLeScanner();

        ArrayList<ScanFilter> filters = new ArrayList<>();
        filters.add(
                new ScanFilter.Builder()
                        .setServiceUuid(
                                new ParcelUuid(UUID.fromString(context.getString(R.string.btmesh_uuid)))
                        )
                        .build()
        );
        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .build();

        btScanner.stopScan(scanCallback);
        btScanner.startScan(filters, settings, scanCallback);
    }

    private static ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            Log.d(TAG, "Device in proximity : " + result.getDevice().getName());
        }
    };


}
