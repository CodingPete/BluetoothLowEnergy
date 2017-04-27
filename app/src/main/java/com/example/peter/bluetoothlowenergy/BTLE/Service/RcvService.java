package com.example.peter.bluetoothlowenergy.BTLE.Service;

import android.Manifest;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.example.peter.bluetoothlowenergy.R;

import java.util.UUID;

/**
 * Created by peter on 21.04.2017.
 */
public class RcvService extends BluetoothGattService {
    /**
     * Create a new BluetoothGattService.
     * <p>Requires {@link Manifest.permission#BLUETOOTH} permission.
     *
     * @param uuid        The UUID for this service
     * @param serviceType The type of this service,
     *                    {@link BluetoothGattService#SERVICE_TYPE_PRIMARY} or
     *                    {@link BluetoothGattService#SERVICE_TYPE_SECONDARY}
     */
    public RcvService(UUID uuid, int serviceType, Context context) {
        super(uuid, serviceType);
        this.addCharacteristic(
                new BluetoothGattCharacteristic(
                        UUID.fromString(context.getString(R.string.btmesh_rcv_characteristic)),
                        BluetoothGattCharacteristic.PROPERTY_WRITE,
                        BluetoothGattCharacteristic.PERMISSION_WRITE
                )
        );
    }
}
