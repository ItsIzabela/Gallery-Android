package com.example.gallery_android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
<<<<<<< HEAD
import com.example.gallery_android.ui.theme.Red
import com.example.gallery_android.ui.theme.Black
import com.example.gallery_android.ui.theme.SmokeWhite
import com.example.gallery_android.ui.theme.Grey
import com.example.gallery_android.ui.theme.GalleryAndroidTheme
import androidx.compose.foundation.lazy.grid.items

=======
import com.example.gallery_android.ui.theme.*
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
>>>>>>> 47a4baa41f96f0de3a3ef879f68d867f648113b9

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
}

<<<<<<< HEAD
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
=======
@Composable
fun ImagePickerScreen() {
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    // wybór wielu zdjęć
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        imageUris = uris
    }

    // edytor zdjęcia (crop)
    val cropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract()
    ) { result ->
        if (result.isSuccessful && selectedIndex != null) {
            val newUri = result.uriContent
            if (newUri != null) {
                imageUris = imageUris.toMutableList().also {
                    it[selectedIndex!!] = newUri
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(containerColor = PinkSand)
        ) {
            Text(
                "Choose your picture to edit",
                color = DarkSand,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = BlueGreen)
>>>>>>> 47a4baa41f96f0de3a3ef879f68d867f648113b9
        ) {

<<<<<<< HEAD
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
=======
        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageUris) { uri ->
                val index = imageUris.indexOf(uri)

                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Chosen",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            selectedIndex = index
                            cropLauncher.launch(
                                CropImageContractOptions(
                                    uri = uri,
                                    cropImageOptions = CropImageOptions()
                                )
                            )
                        }
                )
            }
        }
    }
}
>>>>>>> 47a4baa41f96f0de3a3ef879f68d867f648113b9
