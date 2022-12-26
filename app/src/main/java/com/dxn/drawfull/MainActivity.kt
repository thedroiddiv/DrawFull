/**
 * Copyright (c) 2022 Divyansh Kushwaha <divyanshdxn@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dxn.drawfull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dxn.drawfull.color.ColorPicker
import com.dxn.drawfull.color.colors
import com.dxn.drawfull.drawing.components.FreeHandCanvas
import com.dxn.drawfull.drawing.helper.DrawMode
import com.dxn.drawfull.drawing.helper.rememberDrawing
import com.dxn.drawfull.ui.theme.DrawFullTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawFullTheme {
                val drawing = rememberDrawing()

                var boxHeightPx by remember { mutableStateOf(0f) }
                var offset by remember { mutableStateOf(boxHeightPx) }
                val offsetAnim = animateFloatAsState(targetValue = offset)

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
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Clear"
                                )
                            }
                            IconButton(onClick = { drawing.redo() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
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
        }
    }
}

@Composable
fun DrawModeSelector(
    modifier: Modifier,
    selected: DrawMode,
    polygonSides: Int,
    onSelect: (DrawMode) -> Unit
) {
    Row(
        modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .horizontalScroll(rememberScrollState())
    ) {
        DrawModeButton(drawMode = DrawMode.FREE_HAND, selected = selected, onSelect = onSelect) {
            Icon(
                painter = painterResource(id = R.drawable.ic_free_hand),
                contentDescription = "free hand drawing",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        DrawModeButton(
            drawMode = DrawMode.POLYGON(polygonSides),
            selected = selected,
            onSelect = onSelect
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_polygon),
                contentDescription = "free hand drawing",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        DrawModeButton(drawMode = DrawMode.CIRCLE, selected = selected, onSelect = onSelect) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
            )
        }
        DrawModeButton(drawMode = DrawMode.SQUARE, selected = selected, onSelect = onSelect) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary)
            )
        }
        DrawModeButton(drawMode = DrawMode.RECTANGLE, selected = selected, onSelect = onSelect) {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(16.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}

@Composable
fun DrawModeButton(
    drawMode: DrawMode,
    selected: DrawMode,
    onSelect: (DrawMode) -> Unit,
    content: @Composable () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (selected::class.java == drawMode::class.java) Color.Red else Color.Transparent,
                shape = CircleShape
            ),
        onClick = { onSelect(drawMode) }
    ) { content() }
}