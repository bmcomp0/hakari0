package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ViewModel

@Composable
fun ButtonSheetContent(
    viewModel: ViewModel,
    onClickButton: () -> Unit = {},
    closeButtonOnClick: () -> Unit = {},
) {
    var keyName by remember { viewModel.label }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Row for close button and label
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = closeButtonOnClick) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Text("Enter label name", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(48.dp)) // To balance the close button and next button
        }

        Spacer(modifier = Modifier.height(16.dp))

        // OutlinedTextField
        OutlinedTextField(
            value = keyName,
            onValueChange = { keyName = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Next button aligned to the end
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onClickButton,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(48.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color.White)
            }
        }
    }
}