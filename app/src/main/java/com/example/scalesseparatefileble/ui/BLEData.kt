package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.ViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager


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

    Box(
        modifier = Modifier
            .fillMaxSize(), // てきとう
        contentAlignment = Alignment.BottomCenter
    ) {
        BLEData(
            viewModel = viewModel,
            bluetoothManager = bluetoothManager
        )

        Column {
            BottomSheet(
                viewModel = viewModel,
                bluetoothManager = bluetoothManager
            )
            BottomNavigation(
                viewModel = viewModel,
                onTapBackButton = onTapBackButton,
                onTapNextButton = onTapNextButton
            )
        }
    }
}

@Composable
fun BLEData(viewModel: ViewModel, bluetoothManager: BluetoothManager) {
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

            ContentScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun BottomSheet(
    viewModel: ViewModel,
    bluetoothManager: BluetoothManager
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                ambientColor = Color(0xFF000000),
                elevation = 32.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color(0xFFFFFFFF)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = bluetoothManager.number.value, fontSize = 35.sp)
                Button(
                    onClick = {
                        viewModel.addItem(bluetoothManager.number.value)
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text(text = "追加", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(
    viewModel: ViewModel,
    onTapBackButton: () -> Unit,
    onTapNextButton: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        color = Color(0xFFFFFFFF)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(
                backButtonOnClick = onTapBackButton
            )

            SaveButton(
                saveButtonOnClick = {
                    viewModel.saveDataCsv()
                }
            )

            NextButton(
                nextButtonOnClick = onTapNextButton
            )
        }
    }
}

@Preview
@Composable
fun PreviewThirdScreen() {
//    bottomNavigation()
}
