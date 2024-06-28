package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.ViewModel
import com.example.scalesseparatefileble.util.ColumnItem


@Composable
fun FifthScreen(
    viewModel: ViewModel = hiltViewModel(),
    onTapBackButton: () -> Unit = {},
    onTapHomeButton: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = viewModel.filePath.value,
                fontSize = 20.sp
            )
            CSVDataShow(
                viewModel = viewModel
            )
        }

        BottomNavigation(
            onTapBackButton = onTapBackButton,
            onTapHomeButton = onTapHomeButton
        )
    }
}

@Composable
fun CSVDataShow(
    viewModel: ViewModel,
) {
    val csvDataList = viewModel.csvDataList
    CsvDataListScreen(dataList = csvDataList)
}

@Composable
fun CsvDataListScreen(dataList: List<ColumnItem>) {
    // Scrollable column to display the list
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
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

@Composable
private fun BottomNavigation(
    onTapBackButton: () -> Unit,
    onTapHomeButton: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(
                ambientColor = Color(0xFF000000),
                elevation = 32.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
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

            HomeButton(
                homeButtonOnClick = onTapHomeButton
            )

            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}
