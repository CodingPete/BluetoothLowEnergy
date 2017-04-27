package com.example.peter.bluetoothlowenergy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.peter.bluetoothlowenergy.BTLE.Advertiser;
import com.example.peter.bluetoothlowenergy.BTLE.Scanner;
import com.example.peter.bluetoothlowenergy.BTLE.Server;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wenn Bluetooth Low Energy unterstützt wird ...
        if(check_btle_support()) {

            // BluetoothAdapter holen
            BluetoothAdapter btAdapter = init_bt_adapter();

            // User ggf. Auffordern Bluetooth einzuschalten
            check_bt_turned_on(btAdapter);

            // Wenn Bluetooth eingeschaltet ...
            if(btAdapter.isEnabled()) {

                // Dann den Scanner starten
                new Scanner(getApplicationContext(), btAdapter);

                // GATT-Server starten
                new Server(getApplicationContext(), (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE));

                // Advertising starten
                new Advertiser(getApplicationContext(), btAdapter);
            }
        }
    }

    private boolean check_btle_support() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    private BluetoothAdapter init_bt_adapter() {
        final BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        return manager.getAdapter();
    }

    private void check_bt_turned_on(BluetoothAdapter adapter) {
        if(adapter == null || !adapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 42);
        }
    }

}
