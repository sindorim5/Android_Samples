package com.sindorim.introtocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sindorim.introtocompose.ui.theme.IntroToComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroToComposeTheme {
                MyApp()
            } // End of IntroToComposeTheme
        } // End of setContent
    } // End of onCreate
}

@Composable
fun MyApp() {
    Surface(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        color = Color(0xFF546E7A)
    ) {
        Text(text = "Hello")
    } // End of Surface
}

@Preview
@Composable
fun CreateCircle() {
    Card(
        modifier = Modifier.padding(3.dp).size(45.dp),
        shape = CircleShape
        ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Tap", modifier = Modifier)}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntroToComposeTheme {
        MyApp()
    }
}