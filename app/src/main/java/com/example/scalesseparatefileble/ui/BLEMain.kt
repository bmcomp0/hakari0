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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.model.ViewModel
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
            Text(text = "はかりアプリ EJ-200B", fontSize = 60.sp)

            ConnectDeviceView(
                devices = viewModel.devices.value?.toList() ?: listOf(),
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

            Text(
                text = bluetoothManager.bluetoothUtilities.bleStateMessage.value,
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = nextButtonOnClick,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Next", fontSize = 20.sp, color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }
            }

        }
    }
}
@Composable
fun ConnectDeviceView(
    devices: List<String>,
    scanButtonOnClick: () -> Unit = {},
    connectButtonOnClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFEDEDED)
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
                    contentPadding = PaddingValues(0.dp)
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
                    contentPadding = PaddingValues(0.dp)
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
//                Icon(　// TODO アイコン追加
//                    painter = painterResource(id = R.drawable.ic_device),
//                    contentDescription = "Device Icon",
//                    modifier = Modifier.size(24.dp)
//                )
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

@Composable
fun ButtonSheetContent(
    viewModel: ViewModel,
    onClickButton: () -> Unit = {},
    closeButtonOnClick: () -> Unit = {},
) {
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
                onValueChange = { keyName = it },
                label = { Text(text = "Data Label") },
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