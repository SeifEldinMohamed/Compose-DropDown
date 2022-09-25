package com.seif.composedropdown


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = Color(0xFF101010),
                modifier = Modifier.fillMaxSize()
            ) {
                DropDown(
                    text = "Hello World!",
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "This is now revealed!", modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.Green)
                    )
                }
            }
        }
    }
}

@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    textSize:TextUnit = 16.sp,
    alphaDuration: Int = 300,
    rotateXDuration: Int = 300,
    content: @Composable () -> Unit // composable function
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    val alpha = animateFloatAsState(
        targetValue = if(isOpen) 1f else 0f, // whenever this value changes this function will animate this alpha valuse
        animationSpec = tween(
            durationMillis = alphaDuration
        )
    )
    val rotateX = animateFloatAsState(
        targetValue = if(isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = rotateXDuration
        )
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = textSize
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        isOpen = !isOpen
                    }
                    .scale(1f, if (isOpen) -1f else 1f) // to handle up and down arrow icon
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f) // to make animation done from the start of the box not in the center(default pivot value).
                    rotationX = rotateX.value
                } // allow us to make any kind of 3d transformation
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}