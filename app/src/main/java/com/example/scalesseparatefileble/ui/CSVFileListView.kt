package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ViewModel

@Composable
fun CSVFileListView(
    viewModel: ViewModel,
    onTapBackButton: () -> Unit = {},
    onTapNextButton: () -> Unit = {},
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
                .weight(1f)
        ){
            Text(
                text = "直近の保存：${viewModel.filePath.value}",
                fontSize = 20.sp
            )
            FileLists(
                viewModel = viewModel,
                onTapNextButton = onTapNextButton
            )
        }

        BottomNavigation(
            modifier = Modifier
                .shadow(
                    ambientColor = Color(0xFF000000),
                    elevation = 32.dp,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ),
            isCenterButtonHome = true,
            onTapBackButton = onTapBackButton,
            onTapCenterButton = onTapHomeButton,
            onTapNextButton = onTapNextButton
        )
    }
}

@Composable
fun FileLists(
    viewModel: ViewModel,
    onTapNextButton: () -> Unit = {},
) {
    LaunchedEffect(viewModel) {
        viewModel.getFileList()
    }

    val files = viewModel.csvFileList

    LazyColumn {
        items(files) { fileName ->
            Text(
                text = fileName,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        viewModel.readDataCsv(fileName)
                        onTapNextButton()
                    }
            )
            if (fileName != files.last()) {
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}