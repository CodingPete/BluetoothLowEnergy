package com.example.peter.bluetoothlowenergy.BTLE.Service;

import android.Manifest;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import com.example.peter.bluetoothlowenergy.BTLE.Service.Characteristics.ReadCharacteristic;
import com.example.peter.bluetoothlowenergy.BTLE.Service.Characteristics.ReadWriteCharacteristic;
import com.example.peter.bluetoothlowenergy.BTLE.Service.Characteristics.WriteCharacteristic;
import com.example.peter.bluetoothlowenergy.R;

import java.util.UUID;

/**
 * Created by peter on 21.04.2017.
 */
public class TestService extends BluetoothGattService {
    /**
     * Create a new BluetoothGattService.
     * <p>Requires {@link Manifest.permission#BLUETOOTH} permission.
     *
     * @param uuid        The UUID for this service
     * @param serviceType The type of this service,
     *                    {@link BluetoothGattService#SERVICE_TYPE_PRIMARY} or
     *                    {@link BluetoothGattService#SERVICE_TYPE_SECONDARY}
     */
    public TestService(UUID uuid, int serviceType, Context context) {
        super(uuid, serviceType);

        // Write-Only Characteristic
       this.addCharacteristic(
               new WriteCharacteristic(context)
       );

        // Read-only Characteristic hinzufügen
        this.addCharacteristic(
                new ReadCharacteristic(context)
        );

        // Read-Write Characteristic hinzufügen
        this.addCharacteristic(
                new ReadWriteCharacteristic(context)
        );
    }
}
