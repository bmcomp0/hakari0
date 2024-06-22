package com.example.scalesseparatefileble

// MainActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.model.ViewModel
import com.example.scalesseparatefileble.bluetooth.BluetoothManager
import com.example.scalesseparatefileble.ui.FifthScreen
import com.example.scalesseparatefileble.ui.FirstScreen
import com.example.scalesseparatefileble.ui.FourthScreen
import com.example.scalesseparatefileble.ui.ThirdScreen


class MainActivity : ComponentActivity() {
//    private val messageFromBle: MutableState<String> = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModel(this@MainActivity)
        val bluetoothManager by lazy { BluetoothManager(this, viewModel) }

        setContent {
            NavControllers(viewModel = viewModel, bluetoothManager = bluetoothManager)
        }
    }
}

@Composable
fun NavControllers(viewModel: ViewModel, bluetoothManager: BluetoothManager) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "First") {
        composable(route = "First") {
            FirstScreen(
                viewModel = viewModel,
                bluetoothManager = bluetoothManager,
                navigationController = navController,
            )
        }
        composable(route = "Third"){
            ThirdScreen(
                viewModel = viewModel,
                bluetoothManager = bluetoothManager,
                onTapNextButton = { navController.navigate("Fourth") },
                onTapBackButton = { navController.navigate("First") }
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

