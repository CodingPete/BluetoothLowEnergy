package com.example.peter.bluetoothlowenergy.BTLE.Service.Characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;

import com.example.peter.bluetoothlowenergy.R;

import java.util.UUID;

/**
 * Created by peter on 06.06.2017.
 */

public class ReadCharacteristic extends BluetoothGattCharacteristic {

    public ReadCharacteristic(Context context) {
        super(
                UUID.fromString(context.getString(R.string.btmesh_readonly_characteristic)),
                PROPERTY_READ,
                PERMISSION_READ
        );
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(
                UUID.randomUUID(),
                BluetoothGattDescriptor.PERMISSION_READ
        );
        descriptor.setValue("ReadCharacteristic".getBytes());
        this.addDescriptor(descriptor);
        this.setValue(42, FORMAT_UINT16, 0);
    }
}
