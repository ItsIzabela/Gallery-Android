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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.gallery_android.ui.theme.BlueGreen
import com.example.gallery_android.ui.theme.BrightBlue
import com.example.gallery_android.ui.theme.DarkSand
import com.example.gallery_android.ui.theme.DustyWhite
import com.example.gallery_android.ui.theme.GalleryAndroidTheme
import com.example.gallery_android.ui.theme.PinkSand


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAndroidTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkSand)
                ) {
                    ImagePickerScreen()
                }
            }
        }

    }

@Composable
fun ImagePickerScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkSand,
            )
        ) {
            Text("Choose your picture to edit", color = DarkSand, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueGreen,
            )
        ) {
            Text(":3", color = BrightBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }


        Spacer(modifier = Modifier.height(20.dp))

        imageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Choosen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

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