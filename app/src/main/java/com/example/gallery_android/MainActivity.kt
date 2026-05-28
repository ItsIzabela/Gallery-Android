package com.example.gallery_android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.gallery_android.ui.theme.Red
import com.example.gallery_android.ui.theme.Black
import com.example.gallery_android.ui.theme.SmokeWhite
import com.example.gallery_android.ui.theme.Grey
import com.example.gallery_android.ui.theme.GalleryAndroidTheme
import androidx.compose.foundation.lazy.grid.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAndroidTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Grey)
                ) {
                    ImagePickerScreen()
                }
            }
        }

    }

    @Composable
    fun ImagePickerScreen() {
        var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris: List<Uri> ->
            imageUris = uris
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Grey)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Gallery App",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = SmokeWhite

                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(imageUris) { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Chosen",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { launcher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Select", color = SmokeWhite)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete", color = SmokeWhite)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit", color = SmokeWhite)
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun PreviewImagePicker() {
    GalleryAndroidTheme {
        ImagePickerScreen()
    }
}



}