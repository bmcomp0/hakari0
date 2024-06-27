package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ViewModel

@Composable
fun FourthScreen(
    viewModel: ViewModel,
    onTapBackButton: () -> Unit = {},
    onTapNextButton: () -> Unit = {},
    onTapHomeButton: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
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
            onTapBackButton = onTapBackButton,
            onTapHomeButton = onTapHomeButton
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

    FileList(files = files) { fileName ->
        viewModel.readDataCsv("$fileName")
        onTapNextButton()
    }
}

@Composable
fun FileList(files: List<String>, onFileClick: (String) -> Unit) {
    LazyColumn {
        items(files) { fileName ->
            Text(
                text = fileName,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onFileClick(fileName) }
            )
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
            .height(100.dp),
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
