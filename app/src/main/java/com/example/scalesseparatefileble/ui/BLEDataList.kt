package com.example.scalesseparatefileble.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scalesseparatefileble.util.CustomSwipeToDismiss
import com.example.model.ViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentScreen(viewModel: ViewModel) {
    val listState = rememberLazyListState()
    val items = viewModel.items

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            state = listState
        ) {
            itemsIndexed(items = viewModel.items, key = { _, item -> item.id }) { index, item ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.removeItem(item)
                        }
                        true
                    }
                )
                CustomSwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds =  { FractionalThreshold(0.3f) },
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)) // 最初にclipを適用
                                .background(Color.Red)
                                .padding(4.dp), // clipの後にpaddingを適用
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(end = 20.dp)
                            )
                        }
                    },
                    dismissContent = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "${index + 1}. ", style = MaterialTheme.typography.h6, modifier = Modifier.weight(1f))
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = "${item.value}", style = MaterialTheme.typography.body1, modifier = Modifier.weight(2f))
                            }
                        }
                    }
                )
            }


        }

        LaunchedEffect(items){
            if(items.isNotEmpty()){
                listState.animateScrollBy(
                    value = 200f,
                    animationSpec = tween(durationMillis = 1000)
                )
            }
        }
    }
}