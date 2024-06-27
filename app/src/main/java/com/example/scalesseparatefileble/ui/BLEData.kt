package com.example.scalesseparatefileble.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.ViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThirdScreen(
    viewModel: ViewModel = hiltViewModel(),
    bluetoothManager: BluetoothManager,
    onTapNextButton: () -> Unit = {},
    onTapBackButton: () -> Unit = {}
) {
    LaunchedEffect(Unit, bluetoothManager) {
        bluetoothManager.initializeBluetooth()
    }

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.HalfExpanded)

    val coroutineScope = rememberCoroutineScope()
    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.show() }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            NotificationData(viewModel = viewModel, bluetoothManager = bluetoothManager)
            Button(
                onClick = { viewModel.undoRemoval() },
                modifier = Modifier
                    .padding(vertical = 8.dp)) {
                Text("値を戻す",fontSize = 20.sp)
            }
            Button(
                onClick = {
                    viewModel.saveDataCsv()
//            Toast.makeText(context, "これはトーストです", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Text("保存",fontSize = 20.sp)
            }
            Button(
                onClick = onTapNextButton,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Next",fontSize = 20.sp)
            }
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
    ) {
        BLEData(
            viewModel = viewModel,
            bluetoothManager = bluetoothManager
        )
    }
}

@Composable
fun BLEData(viewModel: ViewModel, bluetoothManager: BluetoothManager){
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
            Text(text = viewModel.label.value, fontSize = 28.sp)

            Box(
                modifier = Modifier
                    .size(480.dp)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ContentScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun NotificationData(viewModel: ViewModel, bluetoothManager: BluetoothManager){
    val number = bluetoothManager.number
    Row{
        Text(text = number.value, fontSize = 35.sp)
        Button(
            onClick = {
                viewModel.addItem(number.value)
                      },
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(text = "追加",fontSize = 20.sp)
        }
    }
}