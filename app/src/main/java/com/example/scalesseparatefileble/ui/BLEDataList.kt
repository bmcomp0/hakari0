package com.example.scalesseparatefileble.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.model.ViewModel
import com.example.scalesseparatefileble.util.CustomSwipeToDismiss

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentScreen(viewModel: ViewModel) {
    val listState = rememberLazyListState()
    val items = viewModel.items

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                                .clip(RoundedCornerShape(14.dp)) // 最初にclipを適用
                                .background(Color.Red)
                                .padding(5.dp), // clipの後にpaddingを適用
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "${index + 1}. ", style = MaterialTheme.typography.h6, modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = item.value, style = MaterialTheme.typography.body1, modifier = Modifier.weight(2f))
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