package com.example.scalesseparatefileble

// MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.example.scalesseparatefileble.ui.BLEData
import com.example.scalesseparatefileble.ui.BLEMain
import com.example.scalesseparatefileble.ui.CSVDataShow
import com.example.scalesseparatefileble.ui.DataLabel


class MainActivity : ComponentActivity() {
//    private val messageFromBle: MutableState<String> = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = SampleViewModel(this@MainActivity)
            val bluetoothManager by lazy { BluetoothManager(this, viewModel) }

            // Initialize Bluetooth
            bluetoothManager.initializeBluetooth()

            NavControllers(viewModel = viewModel, bluetoothManager = bluetoothManager)
        }
    }
}

@Composable
fun NavControllers(viewModel: SampleViewModel, bluetoothManager: BluetoothManager) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "First") {
        composable(route = "First") {
            BLEMain(viewModel = viewModel, bluetoothManager = bluetoothManager, onClickButton = { navController.navigate("Second") })
        }
        composable(route = "Second") {
            DataLabel(viewModel = viewModel, onClickButton = { navController.navigate("Third") })
        }
        composable(route = "Third"){
            BLEData(viewModel = viewModel, bluetoothManager = bluetoothManager, onClickButton = { navController.navigate("Fourth") })
        }
        composable(route = "Fourth"){
            CSVDataShow(viewModel = viewModel)
        }
    }
}










