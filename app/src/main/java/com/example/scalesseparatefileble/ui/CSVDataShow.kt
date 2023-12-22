package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SampleViewModel

@Composable
fun CSVDataShow(viewModel: SampleViewModel){
    viewModel.readDataCsv()
    val csvDataList = viewModel.csvDataList
    CsvDataListScreen(dataList = csvDataList)
}

@Composable
fun CsvDataListScreen(dataList: List<Array<String>>) {
    // Scrollable column to display the list
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(dataList) { rowData ->
            // Row Composable to display each row of data
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display each item in the row
                rowData.forEach { item ->
                    Text(
                        text = item,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}