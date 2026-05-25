package com.example.gallery_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gallery_android.ui.theme.GalleryAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAndroidTheme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {
    Text(
        text = "Hello Gallery!",
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GalleryAndroidTheme {
        Greeting()
    }
}
