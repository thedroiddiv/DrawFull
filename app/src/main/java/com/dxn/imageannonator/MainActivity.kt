package com.dxn.imageannonator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.dxn.imageannonator.ui.components.FreeHandCanvas
import com.dxn.imageannonator.ui.helper.rememberDrawing
import com.dxn.imageannonator.ui.theme.ImageAnnonatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageAnnonatorTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val drawing = rememberDrawing()
                    FreeHandCanvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        drawing = drawing
                    )
                    Button(onClick = { drawing.clear() }) {
                        Text(text = "Clear")
                    }
                }
            }
        }
    }
}
