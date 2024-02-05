package com.dxn.drawfull.ui.screens.drawing

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.dxn.drawfull.color.ColorPicker
import com.dxn.drawfull.color.colors
import com.dxn.drawfull.drawing.components.FreeHandCanvas
import com.dxn.drawfull.drawing.helper.rememberDrawing

@Composable
fun DrawingScreen() {
    val drawing = rememberDrawing()

    var boxHeightPx by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableFloatStateOf(boxHeightPx) }
    val offsetAnim = animateFloatAsState(targetValue = offset, label = "")

    val toggleColorPicker: () -> Unit = {
        offset = if (offset == 0f) {
            boxHeightPx
        } else {
            0f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        FreeHandCanvas(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            drawing = drawing
        )

        Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = offsetAnim.value
                }
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            Row(Modifier.padding(8.dp)) {
                IconButton(onClick = { drawing.clear() }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
                IconButton(onClick = { drawing.undo() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Clear"
                    )
                }
                IconButton(onClick = { drawing.redo() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Clear"
                    )
                }
                IconButton(onClick = toggleColorPicker) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(drawing.color, CircleShape)
                    )
                }
                DrawModeSelector(
                    modifier = Modifier
                        .weight(1f),
                    selected = drawing.drawMode.value,
                    onSelect = { drawing.setDrawMode(it) },
                    polygonSides = 5
                )
            }
            Box(Modifier.onGloballyPositioned {
                boxHeightPx = it.size.height.toFloat()
            }
            ) {
                ColorPicker(
                    colors = colors,
                    selectedColor = drawing.color,
                    onColorPicked = {
                        drawing.setColor(it)
                        toggleColorPicker()
                    }
                )
            }
        }
    }
}

