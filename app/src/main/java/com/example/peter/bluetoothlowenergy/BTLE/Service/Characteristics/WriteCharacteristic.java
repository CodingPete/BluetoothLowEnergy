package com.example.peter.bluetoothlowenergy.BTLE.Service.Characteristics;

import android.Manifest;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;

import com.example.peter.bluetoothlowenergy.R;

import java.util.UUID;

/**
 * Created by peter on 06.06.2017.
 */

public class WriteCharacteristic extends BluetoothGattCharacteristic {

    public WriteCharacteristic(Context context) {
        super(
                UUID.fromString(context.getString(R.string.btmesh_writeonly_characteristic)),
                BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_WRITE
        );
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(
                UUID.randomUUID(),
                BluetoothGattDescriptor.PERMISSION_READ
        );
        descriptor.setValue("WriteCharacteristic".getBytes());
        this.addDescriptor(descriptor);

    }
}
