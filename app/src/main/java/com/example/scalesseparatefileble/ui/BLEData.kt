package com.example.scalesseparatefileble.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager

@Composable
fun BLEData(viewModel: SampleViewModel, bluetoothManager: BluetoothManager, onClickButton: () -> Unit){
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
            Text(text = "Label : ${viewModel.label.value}", fontSize = 20.sp)
            Box(
                modifier = Modifier.size(420.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    List1(viewModel = viewModel)
                }
            }
            NotificationData(viewModel = viewModel, bluetoothManager = bluetoothManager)
            Button(onClick = {
                viewModel.saveDataCsv()
            }) {
                Text(text = "保存")
            }
            Button(onClick = onClickButton) {
                Text(text = "next")
            }
        }
    }
}

@Composable
fun List1(viewModel: SampleViewModel, onClickItem: (Int)-> Unit = {}) {
    val listState = rememberLazyListState()
    val items = viewModel.dataList

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        itemsIndexed(items) { index, item ->
            Row(
                modifier = Modifier.clickable { onClickItem(index) }
            ) {
                Text(text = item, fontSize = 30.sp)
            }
        }
    }


    LaunchedEffect(items.size){
        if(items.size != 0){
            listState.animateScrollBy(
                value = 200f,
                animationSpec = tween(durationMillis = 1000)
            )
        }
    }
}

@Composable
fun NotificationData(viewModel: SampleViewModel, bluetoothManager: BluetoothManager){
    val number = bluetoothManager.number
    Row{
        Text(text = number.value, fontSize = 35.sp)
        Button(onClick = {
            viewModel.addData(number.value)
        }) {
            Text(text = "決定")
        }
    }
}