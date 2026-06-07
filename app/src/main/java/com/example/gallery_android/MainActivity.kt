package com.example.gallery_android

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.gallery_android.ui.theme.Black
import com.example.gallery_android.ui.theme.GalleryAndroidTheme
import com.example.gallery_android.ui.theme.Grey
import com.example.gallery_android.ui.theme.Red
import com.example.gallery_android.ui.theme.SmokeWhite
import com.yalantis.ucrop.UCrop
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAndroidTheme {
                ImageEditorApp()
            }
        }
    }
}

@Composable
fun ImageEditorApp() {
    val context = LocalContext.current

    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    var originalBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var editedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var brightness by remember { mutableStateOf(0f) }   // -1..1
    var contrast by remember { mutableStateOf(1f) }     // 0..2
    var saturation by remember { mutableStateOf(1f) }   // 0..2
    var rotation by remember { mutableStateOf(0f) }     // degrees
    var flipH by remember { mutableStateOf(false) }
    var flipV by remember { mutableStateOf(false) }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        imageUris = uris
        if (uris.isNotEmpty()) {
            selectedUri = uris.first()
        }
    }

    val uCropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val outputUri = UCrop.getOutput(result.data!!)
            if (outputUri != null) {
                selectedUri = outputUri
                loadBitmapFromUri(context, outputUri)?.let { bmp ->
                    val soft = bmp.toMutableSoftware()
                    originalBitmap = soft
                    editedBitmap = soft.toMutableSoftware()
                    brightness = 0f
                    contrast = 1f
                    saturation = 1f
                    rotation = 0f
                    flipH = false
                    flipV = false
                }
            }
        }
    }

    LaunchedEffect(selectedUri) {
        selectedUri?.let { uri ->
            loadBitmapFromUri(context, uri)?.let { bmp ->
                val soft = bmp.toMutableSoftware()
                originalBitmap = soft
                editedBitmap = soft.toMutableSoftware()
                brightness = 0f
                contrast = 1f
                saturation = 1f
                rotation = 0f
                flipH = false
                flipV = false
            }
        }
    }

    LaunchedEffect(originalBitmap, brightness, contrast, saturation, rotation, flipH, flipV) {
        originalBitmap?.let { src ->
            editedBitmap = applyEdits(
                src = src,
                brightness = brightness,
                contrast = contrast,
                saturation = saturation,
                rotation = rotation,
                flipH = flipH,
                flipV = flipV
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Grey)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Gallery Editor",
                color = SmokeWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { pickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("Select", color = SmokeWhite)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(imageUris) { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { selectedUri = uri }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Grey),
            contentAlignment = Alignment.Center
        ) {
            editedBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            } ?: Text("No image selected", color = SmokeWhite)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black)
                .padding(12.dp)
        ) {

            Text("Brightness", color = SmokeWhite)
            Slider(
                value = brightness,
                onValueChange = { brightness = it },
                valueRange = -1f..1f
            )

            Text("Contrast", color = SmokeWhite)
            Slider(
                value = contrast,
                onValueChange = { contrast = it },
                valueRange = 0f..2f
            )

            Text("Saturation", color = SmokeWhite)
            Slider(
                value = saturation,
                onValueChange = { saturation = it },
                valueRange = 0f..2f
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { rotation -= 90f },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Rotate -90°", color = SmokeWhite)
                }

                Button(
                    onClick = { rotation += 90f },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Rotate +90°", color = SmokeWhite)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { flipH = !flipH },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text(if (flipH) "Unflip H" else "Flip H", color = SmokeWhite)
                }

                Button(
                    onClick = { flipV = !flipV },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text(if (flipV) "Unflip V" else "Flip V", color = SmokeWhite)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        selectedUri?.let { uri ->
                            val destFile = File(
                                context.cacheDir,
                                "cropped_${System.currentTimeMillis()}.jpg"
                            )
                            val destUri = Uri.fromFile(destFile)

                            val options = UCrop.Options().apply {
                                setToolbarColor(0xFF080F0F.toInt())
                                setToolbarWidgetColor(0xFFFFFFFF.toInt())
                                setActiveControlsWidgetColor(0xFFFF0000.toInt())
                                setCompressionQuality(95)
                                setFreeStyleCropEnabled(true)
                                setHideBottomControls(false)
                            }

                            val intent = UCrop.of(uri, destUri)
                                .withOptions(options)
                                .getIntent(context)

                            uCropLauncher.launch(intent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Crop", color = SmokeWhite)
                }

                Button(
                    onClick = {
                        editedBitmap?.let { bmp ->
                            saveBitmap(context, bmp)
                            Toast.makeText(
                                context,
                                "Saved to gallery",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Save", color = SmokeWhite)
                }
            }
        }
    }
}

private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
                decoder.isMutableRequired = true
                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE

                val size = info.size
                val maxSize = 2048
                val scale = maxOf(
                    size.width / maxSize.toFloat(),
                    size.height / maxSize.toFloat(),
                    1f
                )

                decoder.setTargetSize(
                    (size.width / scale).toInt(),
                    (size.height / scale).toInt()
                )
            }
        } else {
            @Suppress("DEPRECATION")
            val bmp = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            val maxSize = 2048
            val scale = maxOf(
                bmp.width / maxSize.toFloat(),
                bmp.height / maxSize.toFloat(),
                1f
            )
            Bitmap.createScaledBitmap(
                bmp,
                (bmp.width / scale).toInt(),
                (bmp.height / scale).toInt(),
                true
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun applyEdits(
    src: Bitmap,
    brightness: Float,
    contrast: Float,
    saturation: Float,
    rotation: Float,
    flipH: Boolean,
    flipV: Boolean
): Bitmap {
    val matrix = Matrix().apply {
        postRotate(rotation)
        postScale(if (flipH) -1f else 1f, if (flipV) -1f else 1f)
    }

    val rotated = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)

    val cm = ColorMatrix()

    val c = contrast
    val b = brightness * 255f
    val contrastMatrix = ColorMatrix(
        floatArrayOf(
            c, 0f, 0f, 0f, b,
            0f, c, 0f, 0f, b,
            0f, 0f, c, 0f, b,
            0f, 0f, 0f, 1f, 0f
        )
    )
    cm.postConcat(contrastMatrix)

    val satMatrix = ColorMatrix()
    satMatrix.setSaturation(saturation)
    cm.postConcat(satMatrix)

    val paint = Paint().apply {
        colorFilter = ColorMatrixColorFilter(cm)
        isAntiAlias = true
    }

    val out = Bitmap.createBitmap(rotated.width, rotated.height, Bitmap.Config.ARGB_8888)
    Canvas(out).drawBitmap(rotated, 0f, 0f, paint)

    return out
}

private fun saveBitmap(context: Context, bitmap: Bitmap) {
    val filename = "Edited_${System.currentTimeMillis()}.jpg"
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    )

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
        }
    }
}

private fun Bitmap.toMutableSoftware(): Bitmap {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        if (config == Bitmap.Config.HARDWARE) {

            val softwareBitmap = Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(softwareBitmap)
            canvas.drawBitmap(this, 0f, 0f, null)

            return softwareBitmap
        }
    }

    return copy(Bitmap.Config.ARGB_8888, true)
}
