package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun BLEMain(viewModel: SampleViewModel, bluetoothManager: BluetoothManager, onClickButton: () -> Unit = {}){
    val deviceAddress = bluetoothManager.deviceAddress
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Hello World", fontSize = 50.sp)
            Button(onClick = {
                bluetoothManager.initializeBluetooth()
            }) {
                Text(
                    text = "Adapt"
                )
            }
            Button(onClick = {
                GlobalScope.launch {
                    bluetoothManager.startScanning()
                }
            }) {
                Text(
                    text = "Scan"
                )
            }
            Button(onClick = {
                onClickButton
                GlobalScope.launch {
                    bluetoothManager.connectToDevice(deviceAddress.value)
                }
            }) {
                Text(
                    text = "Connect device"
                )
            }
            Button(onClick = onClickButton) {
                Text(text = "next")
            }
            Text(
                text = bluetoothManager.bluetoothUtilities.bleStateMessage.value,
                fontSize = 20.sp
            )
            DeviceList(viewModel = viewModel)
        }
    }
}

@Composable
fun DeviceList(viewModel: SampleViewModel) {
    val items = viewModel.items

    Column {
        items.value?.forEach { item ->
            Text(item)
        }
    }
}