package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                title = { Text("SecondScreen") },
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
        ) {
            OutlinedTextField(
                value = keyName,
                onValueChange = {keyName = it},
                label = { Text(text = "Data Label" ) }
            )
            Button(onClick = onClickButton) {
                Text(text = "next")
            }
        }
    }
}