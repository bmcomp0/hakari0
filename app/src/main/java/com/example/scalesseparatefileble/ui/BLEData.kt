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
                title = { Text("Label: ${viewModel.label.value}") },
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = bluetoothManager.bluetoothUtilities.bleStateMessage.value, fontSize = 26.sp)
            Box(
                modifier = Modifier.size(420.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ContentScreen(viewModel)
                }
            }
            NotificationData(viewModel = viewModel, bluetoothManager = bluetoothManager)
            ActionButton(viewModel = viewModel)
            Button(onClick = onClickButton) {
                Text(text = "next")
            }
        }
    }
}

@Composable
private fun NotificationData(viewModel: SampleViewModel, bluetoothManager: BluetoothManager){
    val number = bluetoothManager.number
    Row{
        Text(text = number.value, fontSize = 35.sp)
        Button(onClick = {
            viewModel.addItem(number.value)
        }) {
            Text(text = "追加")
        }
    }
}

@Composable
private fun ActionButton(viewModel: SampleViewModel){
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { viewModel.undoRemoval() }) {
            Text("値を戻す")
        }
        Button(onClick = {
            viewModel.saveDataCsv()
//            Toast.makeText(context, "これはトーストです", Toast.LENGTH_SHORT).show()
        }) {
            Text("保存")
        }
    }
}
