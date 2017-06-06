package com.example.peter.bluetoothlowenergy.BTLE.Service.Characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;

import com.example.peter.bluetoothlowenergy.R;

import java.util.UUID;

/**
 * Created by peter on 06.06.2017.
 */

public class ReadWriteCharacteristic extends BluetoothGattCharacteristic {

    public ReadWriteCharacteristic(Context context) {
        super(
                UUID.fromString(context.getString(R.string.btmesh_readwrite_characteristic)),
                PROPERTY_READ | PROPERTY_WRITE,
                PERMISSION_READ | PERMISSION_WRITE
        );
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(
                UUID.randomUUID(),
                BluetoothGattDescriptor.PERMISSION_READ
        );
        descriptor.setValue("ReadWriteCharacteristic".getBytes());
        this.addDescriptor(descriptor);
    }
}
