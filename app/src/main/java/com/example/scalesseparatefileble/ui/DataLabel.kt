package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.SampleViewModel

@Composable
fun DataLabel(viewModel: SampleViewModel, onClickButton: () -> Unit = {}){
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