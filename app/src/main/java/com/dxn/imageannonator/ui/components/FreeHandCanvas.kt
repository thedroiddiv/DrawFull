package com.dxn.imageannonator.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.dxn.imageannonator.ui.helper.Drawing
import com.dxn.imageannonator.ui.helper.createBezierPath

@Composable
fun FreeHandCanvas(
    modifier: Modifier,
    drawing: Drawing
) {
    Canvas(
        modifier = modifier
            .pointerInput(null) {
                detectDragGestures(
                    onDragStart = { drawing.addNewStroke(it) },
                    onDrag = { change, _ ->
                        drawing.continueStroke(change.position)
                    }
                )
            }
    ) {
        drawing.strokes.forEach { stroke ->
            drawPath(
                createBezierPath(stroke.points),
                color = stroke.color,
                alpha = stroke.alpha,
                style = Stroke(
                    width = stroke.width,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}