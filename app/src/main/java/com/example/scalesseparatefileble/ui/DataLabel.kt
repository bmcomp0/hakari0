package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.SampleViewModel

@Composable
fun SecondScreen(
    viewModel: SampleViewModel = hiltViewModel(),
    onTapNextButton: () -> Unit = {},
    onTapBackButton: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                title = { Text("SecondScreen",fontSize = 28.sp) },
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
        DataLabel(
            viewModel = viewModel,
            onClickButton = onTapNextButton,
            paddingValues = it
        )
    }
}
@Composable
fun DataLabel(
    viewModel: SampleViewModel,
    onClickButton: () -> Unit = {},
    paddingValues: PaddingValues
){
    var keyName by remember { viewModel.label }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = keyName,
                onValueChange = {keyName = it},
                label = { Text(text = "Data Label" ) },
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