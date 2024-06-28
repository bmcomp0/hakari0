package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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


@Composable
fun BLEDataView(
    viewModel: ViewModel = hiltViewModel(),
    bluetoothManager: BluetoothManager,
    onTapNextButton: () -> Unit = {},
    onTapBackButton: () -> Unit = {}
) {
    LaunchedEffect(Unit, bluetoothManager) {
        bluetoothManager.initializeBluetooth()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = viewModel.label.value, fontSize = 28.sp) // ラベルタイトル

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                BLEDataList(viewModel = viewModel)
            }

            Column {
                AddDataSheet(
                    viewModel = viewModel,
                    bluetoothManager = bluetoothManager
                )
                BottomNavigation(
                    modifier = Modifier,
                    isCenterButtonHome = false,
                    showNextButton = true,
                    onTapBackButton = onTapBackButton,
                    onTapCenterButton = {
                        viewModel.saveDataCsv()
                    },
                    onTapNextButton = onTapNextButton
                )
            }
        }
    }
}

@Composable
fun AddDataSheet(
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
        Surface(
            modifier = Modifier
                .padding(32.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(152.dp)
                .shadow(
                    ambientColor = Color(0xFFFFFFFF),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                )
        ){
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .height(152.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = bluetoothManager.number.value,
                        fontSize = 40.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(start = 16.dp),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            viewModel.addItem(bluetoothManager.number.value)
                        },
                        enabled = bluetoothManager.number.value != "Invalid Value" && bluetoothManager.number.value != "Not Connected",
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF36BB9C)),
                        shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.5f)
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewThirdScreen() {

}
