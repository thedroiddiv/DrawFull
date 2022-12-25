package com.dxn.imageannonator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.dxn.imageannonator.ui.components.FreeHandCanvas
import com.dxn.imageannonator.ui.helper.rememberDrawing
import com.dxn.imageannonator.ui.theme.ImageAnnonatorTheme


enum class PointPickMode {
    NONE, SQUARE, CIRCLE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageAnnonatorTheme {
                var pointPickingMode by remember { mutableStateOf(PointPickMode.NONE) }
                var selectedPoint by remember { mutableStateOf<Offset?>(null) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val drawing = rememberDrawing()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        FreeHandCanvas(
                            modifier = Modifier.fillMaxSize(),
                            drawing = drawing
                        )
                        if (pointPickingMode != PointPickMode.NONE) {
                            Box(
                                modifier = Modifier
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            selectedPoint = it
                                            // drop the square on canvas
                                            when (pointPickingMode) {
                                                PointPickMode.CIRCLE -> {
                                                    drawing.addCircle(it, 50f)
                                                }
                                                PointPickMode.SQUARE -> {
                                                    drawing.addSquare(it, 50f)
                                                }
                                                else -> {}
                                            }
                                            pointPickingMode = PointPickMode.NONE
                                        }
                                    }
                                    .fillMaxSize()
                                    .background(Color.Black.copy(0.3f))
                            )
                        }
                    }
                    Row {
                        Button(onClick = { drawing.clear() }) {
                            Text(text = "Clear")
                        }
                        Button(onClick = { drawing.undo() }) {
                            Text(text = "Undo")
                        }
                        Button(onClick = { drawing.redo() }) {
                            Text(text = "Redo")
                        }
                    }

                    Row {
                        Button(onClick = { pointPickingMode = PointPickMode.SQUARE }) {
                            Text(text = "Square")
                        }
                        Button(onClick = { pointPickingMode = PointPickMode.CIRCLE }) {
                            Text(text = "Circle")
                        }
                        Button(onClick = { drawing.redo() }) {
                            Text(text = "Quad")
                        }
                    }

                    Text(text = "(${selectedPoint?.x},${selectedPoint?.y})")
                }
            }
        }
    }
}
