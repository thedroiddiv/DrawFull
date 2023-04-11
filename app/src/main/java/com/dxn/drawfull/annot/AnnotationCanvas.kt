package com.dxn.drawfull.annot

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dxn.drawfull.annotation.destinations.ImageAnnotatorDestination


@Composable
fun AnnotationCanvas() {
    Column(Modifier.fillMaxSize().background(Color(0xFF9C9C9C))) {

        val context = LocalContext.current
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        var canvasHeight by remember { mutableStateOf(0.dp) }
        var canvasWidth by remember { mutableStateOf(0.dp) }

        var parentLayoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

        var scale by remember { mutableStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let { imageUri = it }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            imageUri?.let {
                val bitmap: Bitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

                Box(
                    modifier = Modifier.onGloballyPositioned {
                        canvasHeight = it.size.height.dp
                        canvasWidth = it.size.width.dp
                        parentLayoutCoordinates = it
                    }) {
                    AsyncImage(model = it, contentDescription = null)
                    Canvas(modifier = Modifier
                        .width(canvasWidth)
                        .height(canvasHeight)) {
                    }
                }
            }
        }

        Button(onClick = {
            galleryLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "Pick Image")
        }
    }
}