package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scalesseparatefileble.R

@Composable
fun NextButtonWithText(
    nextButtonOnClick: () -> Unit,
) {
    Button(
        onClick = nextButtonOnClick,
        modifier = Modifier
            .height(48.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF36BB9C)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Next", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                modifier = Modifier
                    .fillMaxHeight(),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}

@Composable
fun NextButton(
    nextButtonOnClick: () -> Unit,
) {
    Button(
        onClick = nextButtonOnClick,
        modifier = Modifier
            .width(48.dp)
            .height(48.dp),
        contentPadding = PaddingValues(5.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF36BB9C)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BackButton(
    backButtonOnClick: () -> Unit,
) {
    Button(
        onClick = backButtonOnClick,
        modifier = Modifier
            .height(48.dp)
            .width(48.dp),
        contentPadding = PaddingValues(5.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF36BB9C)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SaveButton(
    saveButtonOnClick: () -> Unit,
) {
    Button(
        onClick = saveButtonOnClick,
        modifier = Modifier
            .width(70.dp)
            .height(70.dp),
        contentPadding = PaddingValues(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF36BB9C)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(id = R.drawable.ic_save),
                contentDescription = "Save",
                tint = Color.White
            )
        }
    }
}

@Composable
fun HomeButton(
    homeButtonOnClick: () -> Unit,
) {
    Button(
        onClick = homeButtonOnClick,
        modifier = Modifier
            .width(70.dp)
            .height(70.dp),
        contentPadding = PaddingValues(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF36BB9C)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(id = R.drawable.ic_home),
                contentDescription = "Home",
                tint = Color.White
            )
        }
    }
}


@Preview
@Composable
fun NextButtonPreview() {
    Row {
        BackButton(backButtonOnClick = {})
        Spacer(modifier = Modifier.width(16.dp))

        SaveButton(saveButtonOnClick = {})

        Spacer(modifier = Modifier.width(16.dp))

        HomeButton(homeButtonOnClick = {})

        Spacer(modifier = Modifier.width(16.dp))

        NextButton(nextButtonOnClick = {})
    }
}