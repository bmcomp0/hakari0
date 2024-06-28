package com.example.scalesseparatefileble.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.model.ViewModel
import kotlin.math.roundToInt

@Composable
fun BLEDataList(viewModel: ViewModel) {
    val listState = rememberLazyListState()
    val items = viewModel.items

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        state = listState
    ) {
        itemsIndexed(items = viewModel.items, key = { _, item -> item.id }) { index, item ->
            LaunchedEffect(items.size) {
                if (items.isNotEmpty()) {
                    listState.animateScrollToItem(items.size - 1)
                }
            }
            val isRevealed = remember {
                mutableStateOf(false)
            }

            SwipeableRow(
                onDeleteConfirmed = {
                    if(isRevealed.value)
                        viewModel.removeItem(item)
                },
                isRevealed = isRevealed
            ) {
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

private enum class OpenedSwipeableState {
    INITIAL,
    OPENED,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableRow(
    onDeleteConfirmed: () -> Unit,
    isRevealed: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    val swipeableState = rememberSwipeableState(
        initialValue = OpenedSwipeableState.INITIAL,
        confirmStateChange = {
             when (it) {
                OpenedSwipeableState.INITIAL     -> {
                    isRevealed.value = false
                }

                OpenedSwipeableState.OPENED      -> {
                    isRevealed.value = true
                }
            }
            true
        }
    )

    BoxWithConstraints {
        val constraintsScope = this
        val maxWidthPx = with(LocalDensity.current) {
            constraintsScope.maxWidth.toPx()
        }
        val deleteButtonWidth = 72.dp
        val deleteButtonWidthPx = with(LocalDensity.current) {
            deleteButtonWidth.toPx()
        }

        Box(
            Modifier
                .swipeable(
                    state = swipeableState,
                    orientation = Orientation.Horizontal,
                    reverseDirection = true,
                    anchors = mapOf(
                        0f to OpenedSwipeableState.INITIAL,
                        deleteButtonWidthPx to OpenedSwipeableState.OPENED
                    )
                )
        ) {
            DeleteButtonLayout( // 後から出てくる削除ボタン
                onDeleteConfirmed = onDeleteConfirmed,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .offset { IntOffset(-swipeableState.offset.value.roundToInt(), 0) }
            ) {
                content() //表側に表示するレイアウト
            }
        }
    }
}

@Composable
fun DeleteButtonLayout(
    onDeleteConfirmed: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp)) // 最初にclipを適用
            .background(Color.Red)
            .padding(5.dp), // clipの後にpaddingを適用
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable {
                    onDeleteConfirmed()
                },
        )
    }
}
