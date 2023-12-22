package com.example.scalesseparatefileble.bluetooth

import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.util.BluetoothUtilities
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class BluetoothManager(
    private val context: Context,
    private val viewModel: SampleViewModel
) {
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler()

    // Other Bluetooth-related constants and variables...
    private val SCAN_PERIOD: Long = 3000
    private val messageFromBle: MutableState<String> = mutableStateOf("")
    private val flag: MutableState<Int> = mutableStateOf(0)
    val deviceAddress: MutableState<String> = mutableStateOf("")
    private val bluetoothStatus = MutableLiveData<Boolean>()
    private val hash: MutableState<String> = mutableStateOf("")
    val number: MutableState<String> = mutableStateOf("")

    val bluetoothUtilities = BluetoothUtilities

    fun initializeBluetooth() {
        // Check Bluetooth permissions
        checkBluetoothPermissions()

        // Set initial Bluetooth status
        bluetoothStatus.value = isBluetoothEnabled()

        // Initialization code...
    }

    fun startScanning() {
        // Scanning code...
        bluetoothLeScanner?.let { scanner ->
            if (!scanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    scanning = false
                    scanner.stopScan(leScanCallback)
                    println("stopScan")
                }, SCAN_PERIOD)
                scanning = true
                scanner.startScan(leScanCallback)
                println("startScan")
            } else {
                scanning = false
                scanner.stopScan(leScanCallback)
                println("stopScan")
            }
        }

    }

    fun connectToDevice(deviceAddress: String) {
        // Connection code...
        val device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        if(device == null){
            Log.d("device.connection", "デバイスが見つかりません")
        }else{
            var bluetoothGatt = device.connectGatt(context, false, mGattCallback)
        }

    }

    private fun checkBluetoothPermissions() {
        val permissions = arrayOf(
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.BLUETOOTH_ADVERTISE
        )

        val permissionNotGrantedList = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionNotGrantedList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                context as Activity,
                permissionNotGrantedList.toTypedArray(),
                100
            )
        } else {
            // All permissions are already granted.
            bluetoothUtilities.bleStateMessageChange(1)
        }
    }

    private fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        // Scan callback implementation...
        private val TAG = "result.device.name"
        private var device = ""
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if( result == null){
                println("result : null")
            }
            if(result.device.name != null){
                device = result.device.name
            }

            //nameをログで出力する。nullだった場合No Name
            Log.d(TAG, result.device.name ?: "No Name")

            if(result.device.name == "MyBLEDevice"){
                Log.d("result.device.UUID", "$result")
                Log.d("result.device.address", result.device.address)
                deviceAddress.value = result.device.address
                bluetoothUtilities.bleStateMessageChange(2)
            }
            viewModel.addDevice(device)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d(TAG,"error")
        }
    }

    private val mGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        // GATT callback implementation...
        private val TAG = "bluetooth.gatt.service"

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            Log.d(TAG, newState.toString())
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d("$TAG.callback", "接続しました : GATT取得完了")
                gatt?.discoverServices()
                bluetoothUtilities.bleStateMessageChange(3)
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d("$TAG.callback", "切断しました")
                bluetoothUtilities.bleStateMessageChange(4)
                flag.value = 0
            } else if (status == 133 && newState == BluetoothProfile.STATE_DISCONNECTED) {
                bluetoothUtilities.bleStateMessageChange(5)
                Log.d("$TAG.error", "133 error")
//                connectDevice()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
//            Log.d(TAG, "$gatt")
            Log.d("$TAG.status", "$status")
            if (status == BluetoothGatt.GATT_SUCCESS) {
                var bleList = gatt?.services
                println(bleList)
                println(bleList?.get(2)?.uuid)

                Log.d(TAG, "GATT_SUCCESS")
                val service =
                    gatt?.getService(UUID.fromString("55725ac1-066c-48b5-8700-2d9fb3603c5e"))
                if (service != null) {
                    //characteristic等の取得
                    var bleCharList = service.characteristics
                    println(bleCharList?.get(0)?.uuid)
                    println(bleCharList?.get(0)?.properties)
                    if (bleCharList?.get(0)?.properties == 16) {
                        println("Notify")
                    }
                    val gattNotifyCharacteristic =
                        service.getCharacteristic(UUID.fromString("69ddb59c-d601-4ea4-ba83-44f679a670ba"))
                    if (gattNotifyCharacteristic != null) {
                        Log.d("bluetooth.gatt.notify", "characteristic取得完了")
                        //read
                        readFunction(gatt, gattNotifyCharacteristic)

                        //notifyの設定
                        val registered =
                            gatt?.setCharacteristicNotification(gattNotifyCharacteristic, true)
                        Log.d("bluetooth.gatt.notify", "$registered")
                    } else
                        Log.d(TAG, "notify does not found")
                } else {
                    Log.d(TAG, "service isn't here")
                }
            } else
                Log.d(TAG, "GATT server can't get")
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, value, status)
            val stringValue = String(value)
            Log.d("bluetooth.gatt.read", stringValue)
            when(flag.value){
                1 -> {
                    hash.value = bluetoothUtilities.md5(stringValue)
                    println(hash.value)
                    GlobalScope.launch {
                        delay( 500)
                        writeFunction(gatt, characteristic, hash.value)
                    }
                }
                2 -> {
                    if(stringValue == "next"){
                        GlobalScope.launch {
                            delay(500)
                            writeFunction(gatt, characteristic, "test2")
                        }
                        bluetoothUtilities.bleStateMessageChange(7)
                    }
                }
                3 -> {
                    hash.value = bluetoothUtilities.md5("test2")
                    println(stringValue == hash.value)
                    if(stringValue == hash.value){
                        GlobalScope.launch {
                            delay( 1000)
                            writeFunction(gatt, characteristic, "ok")
                        }
                        bluetoothUtilities.bleStateMessageChange(8)
                    }else{
                        gatt.disconnect()
                    }
                }
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            super.onCharacteristicChanged(gatt, characteristic, value)
            val stringValue = String(value)
            Log.d("bluetooth.gatt.notify", stringValue)
            bluetoothUtilities.bleStateMessageChange(6)
            // Update the list with the received notification data
            number.value = stringValue
        }


        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            Log.d("bluetooth.write.characteristic", "success: $status")
            if(status == 0){
//                if(flag.value ==  0 || flag.value == 1){
                if(flag.value ==  0 ){
                    GlobalScope.launch {
                        delay(500)
                        readFunction(gatt, characteristic)
                    }
                }
                when(flag.value){
                    1 ->{
                        GlobalScope.launch {
                            delay(500)
//                            writeFunction(gatt, characteristic!!, "test2")
                            readFunction(gatt, characteristic)
                        }
                    }
                    2 ->{
                        GlobalScope.launch {
                            delay(500)
                            readFunction(gatt, characteristic)
                        }

                    }
                }

            }
        }

        private fun writeFunction(
            gatt: BluetoothGatt?,
            gattNotifyCharacteristic: BluetoothGattCharacteristic,
            string: String
        ){
            //write
            val charset = Charsets.UTF_8
            val data = string.toByteArray(charset = charset)
            gatt?.writeCharacteristic(gattNotifyCharacteristic, data, BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE)
        }

        private fun readFunction(
            gatt: BluetoothGatt?,
            gattNotifyCharacteristic: BluetoothGattCharacteristic?,
        ){
            flag.value += 1
            gatt?.readCharacteristic(gattNotifyCharacteristic)
        }
    }

    // Other Bluetooth-related functions...
}
