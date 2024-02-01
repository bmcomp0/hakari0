package com.example.scalesseparatefileble.ui

import android.view.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun FirstScreen(
    viewModel: SampleViewModel = hiltViewModel(),
    bluetoothManager: BluetoothManager,
    onTapNextButton: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                title = { Text("FirstScreen") },
                actions = {
                    IconButton(onClick = { onTapNextButton() }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "次へ")
                    }
                }
            )
        }
    ) {
        BLEMain(
            viewModel = viewModel,
            bluetoothManager = bluetoothManager,
            onClickButton = onTapNextButton,
            paddingValues = it
        )
    }
}

@Composable
fun BLEMain(
    viewModel: SampleViewModel,
    bluetoothManager: BluetoothManager,
    onClickButton: () -> Unit = {},
    paddingValues: PaddingValues
){
    // Initialize Bluetooth when the Composable is used
    LaunchedEffect(bluetoothManager) {
        bluetoothManager.initializeBluetooth()
    }

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
                GlobalScope.launch {
                    bluetoothManager.startScanning()
                }
            }) {
                Text(
                    text = "Scan"
                )
            }
            Button(onClick = {
//                onClickButton()
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
    val items = viewModel.devices

    Column {
        items.value?.forEach { item ->
            Text(item)
        }
    }
}