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

    private static BluetoothGattServer server;

    public Server(Context context, BluetoothManager btManager) {
        server = btManager.openGattServer(context, callback);
        server.addService(
          new TestService(
                  UUID.fromString(context.getString(R.string.btmesh_uuid)),
                  BluetoothGattService.SERVICE_TYPE_PRIMARY,
                  context
          )
        );

        // Jetzt wird ein minimaler Battery-Service entsprechend der Bluetooth Spezifikation hinzugefügt.
        // Base-UUID : 	00000000-0000-1000-8000-00805F9B34FB
        // UUID berechnen : Assigned Number * 2^96 + Base-UUID
        // 16 Bit Assigned Number : 0000xxxx-0000-1000-8000-00805F9B34FB
        // 32 Bit Assigned Number : xxxx0000-0000-1000-8000-00805F9B34FB

        // 4 Digits hinzufügen um Batteryservice UUID zu bekommen 0x180F 16bit lang
        // BatteryService-UUID : 	0000180F-0000-1000-8000-00805F9B34FB
        BluetoothGattService batteryService = new BluetoothGattService(UUID.fromString("0000180F-0000-1000-8000-00805F9B34FB"), BluetoothGattService.SERVICE_TYPE_PRIMARY);

        // Batterylevel Characteristic hinzufügen Assigned Number: 0x2A19
        BluetoothGattCharacteristic batteryCharacteristic = new BluetoothGattCharacteristic(UUID.fromString("00002A19-0000-1000-8000-00805F9B34FB"), BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ);
        batteryCharacteristic.setValue(45, BluetoothGattCharacteristic.FORMAT_UINT8, 0);
        batteryService.addCharacteristic(batteryCharacteristic);
        server.addService(batteryService);
    }

    private static BluetoothGattServerCallback callback = new BluetoothGattServerCallback() {
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

            if(device.getBondState() == BluetoothDevice.BOND_NONE) {
                device.createBond();
                server.sendResponse(device, requestId, BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION, offset, value);
            }
            else {
                characteristic.setValue(value);
                server.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
            }

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
