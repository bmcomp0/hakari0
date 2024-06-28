package com.example.scalesseparatefileble.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
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
fun BottomNavigation(
    modifier: Modifier = Modifier,
    isCenterButtonHome: Boolean = false,
    showNextButton: Boolean = false,
    onTapBackButton: () -> Unit,
    onTapCenterButton: () -> Unit,
    onTapNextButton: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        color = Color(0xFFFFFFFF)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(
                backButtonOnClick = onTapBackButton
            )

            if (isCenterButtonHome) {
                HomeButton(
                    homeButtonOnClick = onTapCenterButton
                )
            } else {
                SaveButton(
                    saveButtonOnClick = onTapCenterButton
                )
            }

            if(showNextButton) {
                NextButton(
                    nextButtonOnClick = onTapNextButton
                )
            }
            else {
                Spacer(modifier = Modifier.width(60.dp))
            }
        }
    }
}


@Composable
fun NextButtonWithText(
    nextButtonOnClick: () -> Unit,
) {
    Button(
        onClick = nextButtonOnClick,
        modifier = Modifier
            .padding(16.dp)
            .height(60.dp),
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
            .width(60.dp)
            .height(60.dp),
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
            .height(60.dp)
            .width(60.dp),
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
            .width(80.dp)
            .height(80.dp),
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
            .width(80.dp)
            .height(80.dp),
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