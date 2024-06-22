package com.example.scalesseparatefileble.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.model.ViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.DelicateCoroutinesApi
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
                    viewModel.addItem(viewModel.label.value)
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
        modifier = Modifier.fillMaxSize(),
    ) {
        BLEMain(
            viewModel = viewModel,
            bluetoothManager = bluetoothManager,
            onClickButton = {
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
    onClickButton: () -> Unit = {},
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
            Text(text = "はかりアプリ EJ-200B", fontSize = 60.sp)

            ConnectDeviceView(
                scanButtonOnClick = {
//                    GlobalScope.launch {
//                    if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED){
//                        bluetoothManager.initializeBluetooth()
//                    }else{
//                        bluetoothManager.startScanning()
//                    }
//                }
                },
                connectButtonOnClick = {
//                GlobalScope.launch {
//                    bluetoothManager.connectToDevice(deviceAddress.value)
//                }
                },
            )

            Text(
                text = bluetoothManager.bluetoothUtilities.bleStateMessage.value,
                fontSize = 24.sp
            )

            Row {
                DeviceList(devices = viewModel.devices.value ?: emptyList())

                Button(
                    onClick = {
                        onClickButton()
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Next", fontSize = 20.sp)
                }
            }

        }
    }
}

@Composable
fun ConnectDeviceView(
    scanButtonOnClick: () -> Unit = {},
    connectButtonOnClick: () -> Unit = {},
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(300.dp)
    ) {
        DeviceList(
            devices = listOf("Device 1", "Device 2", "Device 3", "Device 4") // TODO
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // Add your scanning logic here
                },
                modifier = Modifier
                    .height(48.dp)
                    .background(Color(0xFF4CAF50)),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Scan", fontSize = 20.sp, color = Color.White)
            }
            Button(
                onClick = {
                    // Add your connect logic here
                },
                modifier = Modifier
                    .height(48.dp)
                    .background(Color(0xFF4CAF50)),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Connect", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun DeviceList(
    devices: List<String>,
) {
    // devices = listOf("Device 1", "Device 2", "Device 3", "Device 4")
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(devices.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = devices[index], fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun ButtonSheetContent(
    viewModel: ViewModel,
    onClickButton: () -> Unit = {},
    closeButtonOnClick: () -> Unit = {},
){
    var keyName by remember { viewModel.label }

    Column(
        modifier = Modifier
            .padding(start = 12.dp, bottom = 24.dp)
            .height(300.dp),
    ) {
        Row {
            Text("Enter Label Name")
            Button(onClick = { closeButtonOnClick() }) {
                Text("Close")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = keyName,
                onValueChange = {keyName = it},
                label = { Text(text = "Data Label" ) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
            Button(
                onClick = onClickButton,
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(text = "Next", fontSize = 20.sp)
            }
        }

    }
}


@Composable
@Preview
fun PreviewBLEMain() {
    ConnectDeviceView()
}