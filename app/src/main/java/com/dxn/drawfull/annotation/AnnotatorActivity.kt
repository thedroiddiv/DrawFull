package com.dxn.drawfull.annotation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import com.dxn.drawfull.annot.ZoomableImage
import com.dxn.drawfull.annotation.destinations.ImageAnnotatorDestination
import com.dxn.drawfull.ui.theme.DrawFullTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class AnnotatorActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawFullTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}


@Composable
@Destination(start = true)
fun Start(navigator: DestinationsNavigator) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { navigator.navigate(ImageAnnotatorDestination(uri)) }
    }

    Button(onClick = {
        galleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
    ) {
        Text(text = "Pick Image")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun ImageAnnotator(navigator: DestinationsNavigator, imageUri: Uri) {
    val polygonDrawer = remember {
        PolygonDrawer(
            Color.Red,
            4f,
            1f,
            22f
        )
    }
//    ImageAnnotationCanvas(imageUri = imageUri, polygonDrawer = polygonDrawer)
    val painter = rememberImagePainter(data = imageUri)
    ZoomableImage(painter = painter)
}


