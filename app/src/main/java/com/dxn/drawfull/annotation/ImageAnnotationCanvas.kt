package com.dxn.drawfull.annotation

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageAnnotationCanvas(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    polygonDrawer: PolygonDrawer
) {
    var parentHeight by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableStateOf(0.dp) }

    var canvasHeight by remember { mutableStateOf(0.dp) }
    var canvasWidth by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }


    val state =
        rememberTransformableState { zoomChange, offsetChange, _ ->
            scale *= zoomChange
            offset += offsetChange
        }


    Box(
        modifier
            .fillMaxSize()
            .onGloballyPositioned {
                parentHeight = it.size.height.dp
                parentWidth = it.size.width.dp
            },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .graphicsLayer {
                scaleX = maxOf(.5f, minOf(3f, scale))
                scaleY = maxOf(.5f, minOf(3f, scale))
                translationX = offset.x
                translationY = offset.y
            }
            .border(2.dp, Color.Red)
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = "image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .border(2.dp, Color.Green)
                    .onGloballyPositioned {
                        canvasHeight = with(density) { it.size.height.toDp() }
                        canvasWidth = with(density) { it.size.width.toDp() }
                    }
            )
            Canvas(
                modifier = Modifier
                    .width(canvasWidth)
                    .height(canvasHeight)
                    .border(2.dp, Color.Blue)
                    .pointerInput(null) {
                        detectTapGestures { offset ->
                            polygonDrawer.create(5, offset)
                        }
                    }
                    .pointerInput(null) {
                        detectDragGestures(
                            onDragStart = {
                                polygonDrawer.onDragStarted(it)
                            },
                            onDrag = { change, _ ->
                                polygonDrawer.onDragging(change.position)
                            },
                            onDragEnd = {
                                polygonDrawer.onDragEnd()
                            }
                        )
                    }
                    .transformable(state)
            ) {
                polygonDrawer.strokes.forEach {
                    val vertices = it.vertices
                    drawPath(
                        Path().apply {
                            if (vertices.isNotEmpty()) {
                                val p = vertices.toMutableList().apply { add(vertices[0]) }
                                drawLinear(p)
                            }
                        },
                        color = Color.Red,
                        alpha = 1f,
                        style = Stroke(
                            width = 3f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                    it.vertices.forEach {
                        drawCircle(
                            brush = SolidColor(Color.Red),
                            radius = polygonDrawer.vertexRadius,
                            center = it
                        )
                    }
                }
            }
        }
    }
}

fun Path.drawLinear(points: List<Offset>) {
    if (points.size <= 1) return // need atLeast two points to draw path
    moveTo(points[0].x, points[0].y)
    points.forEach { lineTo(it.x, it.y) }
}