package com.example.scalesseparatefileble.ui

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.ViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun FirstScreen(
    viewModel: ViewModel = hiltViewModel(),
    bluetoothManager: BluetoothManager,
    onTapNextButton: () -> Unit = {}
) {
    // Initialize Bluetooth when the Composable is used
    LaunchedEffect(Unit,bluetoothManager) {
        bluetoothManager.initializeBluetooth()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                title = { Text("FirstScreen",fontSize = 28.sp) },
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
    viewModel: ViewModel,
    bluetoothManager: BluetoothManager,
    onClickButton: () -> Unit = {},
    paddingValues: PaddingValues
){
    val deviceAddress = bluetoothManager.deviceAddress
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "はかりアプリ EJ-200B", fontSize = 60.sp)
            Button(
                onClick = {
                    GlobalScope.launch {
                        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED){
                            bluetoothManager.initializeBluetooth()
                        }else{
                            bluetoothManager.startScanning()
                        }
                    }
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)) {
                Text(text = "Scan", fontSize = 20.sp)
            }
            Button(onClick = {
                    GlobalScope.launch {
                        bluetoothManager.connectToDevice(deviceAddress.value)
                    }
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)) {
                Text(text = "Connect device",fontSize = 20.sp)
            }
            Button(
                onClick = onClickButton,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Next",fontSize = 20.sp)
            }
            Text(
                text = bluetoothManager.bluetoothUtilities.bleStateMessage.value,
                fontSize = 24.sp
            )
            DeviceList(viewModel = viewModel)
        }
    }
}

@Composable
fun DeviceList(viewModel: ViewModel) {
    val items = viewModel.devices

    Column {
        items.value?.forEach { item ->
            Text(item)
        }
    }
}