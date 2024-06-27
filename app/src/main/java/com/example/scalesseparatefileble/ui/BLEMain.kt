package com.example.scalesseparatefileble.ui

import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.model.ViewModel
import com.example.scalesseparatefileble.R
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FirstScreen(
    viewModel: ViewModel = hiltViewModel(),
    bluetoothManager: BluetoothManager,
    navigationController: NavController,
) {
    LaunchedEffect(Unit, bluetoothManager) {
        bluetoothManager.initializeBluetooth()
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it == ModalBottomSheetValue.Hidden },
        skipHalfExpanded = false
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            ButtonSheetContent(
                viewModel = viewModel,
                onClickButton = {
                    navigationController.navigate("Third")
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
                closeButtonOnClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }
            )
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
    ) {
        BLEMain(
            viewModel = viewModel,
            bluetoothManager = bluetoothManager,
            nextButtonOnClick = {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                    else sheetState.show()
                }
            },
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun BLEMain(
    viewModel: ViewModel,
    bluetoothManager: BluetoothManager,
    nextButtonOnClick: () -> Unit = {},
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            TitleView(title = "重量記録アプリ　EJ-200B", appVersion = "v1.0.0")

            ConnectDeviceView( // TODO BLEデバイスのみ表示
//                devices = viewModel.devices.value?.toList() ?: listOf("device_1", "device_2", "device_3", "device_4"),
                devices = listOf("device_1", "device_2", "device_3", "device_4"),
                scanButtonOnClick = {
                    GlobalScope.launch {
                        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED){
                            bluetoothManager.initializeBluetooth()
                        }else{
                            bluetoothManager.startScanning()
                        }
                    }
                },
                connectButtonOnClick = {
                    GlobalScope.launch {
                        bluetoothManager.connectToDevice(deviceAddress.value)
                    }
                },
            )

            // BLE ステータス
            Text(
                text = bluetoothManager.bluetoothUtilities.bleStateMessage.value,
                fontSize = 24.sp
            )

            DeviceList(devices = viewModel.devices.value?.toList() ?: listOf())

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                NextButtonWithText(nextButtonOnClick = nextButtonOnClick)
            }
        }
    }
}

@Composable
fun TitleView(
    title: String,
    appVersion: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)

    ) {
        Text(text = title, fontSize = 32.sp)
        Text(text = appVersion, fontSize = 16.sp)
    }
}

@Composable
fun ConnectDeviceView(
    devices: List<String>,
    scanButtonOnClick: () -> Unit = {},
    connectButtonOnClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .shadow(
                ambientColor = Color(0xFF000000),
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFFFFFF)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            ConnectableDeviceList(devices = devices)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = scanButtonOnClick,
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
                ) {
                    Text(text = "Scan", fontSize = 20.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(380.dp))

                Button(
                    onClick = connectButtonOnClick,
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
                ) {
                    Text(text = "Connect", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ConnectableDeviceList(
    devices: List<String>,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(devices) { index, device ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_device),
                    contentDescription = "Bluetooth",
                    tint = Color(0xFF75787A)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = device, fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun DeviceList(
    devices: List<String>,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        itemsIndexed(devices) { _, device ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = device, fontSize = 8.sp, color = Color.Black)
            }
        }
    }
}

@Preview
@Composable
fun PreviewFirstScreen() {
    Column {
        TitleView(title = "はかりアプリ EJ-200B", appVersion = "v1.0.0")

        ConnectDeviceView(
            devices = listOf("device_1", "device_2", "device_3", "device_4"),
            scanButtonOnClick = {},
            connectButtonOnClick = {},
        )
    }
}

