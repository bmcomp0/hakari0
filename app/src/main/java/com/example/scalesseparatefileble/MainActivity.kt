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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.model.SampleViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scalesseparatefileble.ui.*
//import com.example.scalesseparatefileble.ui.BLEData
//import com.example.scalesseparatefileble.ui.BLEMain
//import com.example.scalesseparatefileble.ui.CSVDataShow
//import com.example.scalesseparatefileble.ui.DataLabel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
            FirstScreen(
                viewModel = viewModel,
                bluetoothManager = bluetoothManager,
                onTapNextButton = { navController.navigate("Second") }
            )
        }
        composable(route = "Second") {
            SecondScreen(
                viewModel = viewModel,
                onTapNextButton = { navController.navigate("Third") },
                onTapBackButton = { navController.navigate("First") }
            )
        }
        composable(route = "Third"){
            ThirdScreen(
                viewModel = viewModel,
                bluetoothManager = bluetoothManager,
                onTapNextButton = { navController.navigate("Fourth") },
                onTapBackButton = { navController.navigate("Second") }
            )
        }
        composable(route = "Fourth"){
            FourthScreen(
                viewModel = viewModel,
                onTapBackButton = { navController.navigate("Third") },
                onTapNextButton = { navController.navigate("Fifth") }
            )
        }
        composable(route = "Fifth"){
            FifthScreen(
                viewModel = viewModel,
                onTapBackButton = { navController.navigate("Fourth") }
            )
        }
    }
}

