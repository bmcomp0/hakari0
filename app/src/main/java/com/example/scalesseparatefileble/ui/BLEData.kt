package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager


@Composable
fun ThirdScreen(
    viewModel: SampleViewModel = hiltViewModel(),
    bluetoothManager: BluetoothManager,
    onTapNextButton: () -> Unit = {},
    onTapBackButton: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                title = { Text(
                    "Label: ${viewModel.label.value}",
                    fontSize = 28.sp
                ) },
                navigationIcon = {
                    IconButton(onClick = { onTapBackButton() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    IconButton(onClick = { onTapNextButton() }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "次へ")
                    }
                }
            )
        }
    ) {
        BLEData(
            viewModel = viewModel,
            bluetoothManager = bluetoothManager,
            onClickButton = onTapNextButton,
            paddingValues = it
        )

    }
}

@Composable
fun BLEData(viewModel: SampleViewModel, bluetoothManager: BluetoothManager, onClickButton: () -> Unit, paddingValues: PaddingValues){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = bluetoothManager.bluetoothUtilities.bleStateMessage.value,
                fontSize = 28.sp)
            Box(
                modifier = Modifier
                    .size(480.dp)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ContentScreen(viewModel)
                }
            }
            NotificationData(viewModel = viewModel, bluetoothManager = bluetoothManager)
            OutlinedButton(
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
                onClick = onClickButton,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Next",fontSize = 20.sp)
            }
        }
    }
}

@Composable
private fun NotificationData(viewModel: SampleViewModel, bluetoothManager: BluetoothManager){
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