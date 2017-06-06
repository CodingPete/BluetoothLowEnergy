package com.example.peter.bluetoothlowenergy.BTLE;

import android.bluetooth.*;
import android.content.Context;
import android.util.Log;

import com.example.peter.bluetoothlowenergy.BTLE.Service.TestService;
import com.example.peter.bluetoothlowenergy.R;

import java.util.UUID;

/**
 * Created by peter on 21.04.2017.
 */
public class Server {

    private BluetoothGattServer server;
    private Context context;

    public Server(Context context, BluetoothManager btManager) {
        server = btManager.openGattServer(context, callback);
        this.context = context;
        server.addService(
          new TestService(
                  UUID.fromString(context.getString(R.string.btmesh_uuid)),
                  BluetoothGattService.SERVICE_TYPE_PRIMARY,
                  context
          )
        );
    }

    private BluetoothGattServerCallback callback = new BluetoothGattServerCallback() {
        private String TAG = "Server";
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            Log.d(TAG, "Connection : " + device.getName());
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            server.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
            characteristic.setValue(value);
            server.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
            Log.d(TAG, new String(value));
        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
            server.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, descriptor.getValue());
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            super.onExecuteWrite(device, requestId, execute);
            Log.d(TAG, "EXECUTE WRITE : " + device.getName());
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            super.onMtuChanged(device, mtu);
        }
    };
}
