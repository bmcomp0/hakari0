package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SampleViewModel

@Composable
fun FourthScreen(
    viewModel: SampleViewModel,
    onTapBackButton: () -> Unit = {},
    onTapNextButton: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.LightGray,
                title = { Text("File List",fontSize = 28.sp) },
                navigationIcon = {
                    IconButton(onClick = { onTapBackButton() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "直近の保存：${viewModel.filePath.value}",
                fontSize = 20.sp
            )
            FileLists(
                viewModel = viewModel,
                onTapNextButton = onTapNextButton,
                paddingValues = it)
        }
    }
}

@Composable
fun FileLists(
    viewModel: SampleViewModel,
    onTapNextButton: () -> Unit = {},
    paddingValues: PaddingValues
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
