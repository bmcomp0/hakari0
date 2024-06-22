package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scalesseparatefileble.util.ColumnItem
import com.example.model.ViewModel


@Composable
fun FifthScreen(
    viewModel: ViewModel = hiltViewModel(),
    onTapBackButton: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                title = { Text("File: ${viewModel.readingCsvFile.value}") },
                navigationIcon = {
                    IconButton(onClick = { onTapBackButton() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) {
        CSVDataShow(
            viewModel = viewModel,
            paddingValues = it
        )
    }
}

@Composable
fun CSVDataShow(
    viewModel: ViewModel,
    paddingValues: PaddingValues
) {
    val csvDataList = viewModel.csvDataList
    CsvDataListScreen(dataList = csvDataList)
}

@Composable
fun CsvDataListScreen(dataList: List<ColumnItem>) {
    // Scrollable column to display the list
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(items = dataList, key = { _, item -> item.id }) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), // Adjusted bottom padding
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${index + 1}. ",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${item.value}", // Updated to use item.text instead of item.value
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
    }
}
