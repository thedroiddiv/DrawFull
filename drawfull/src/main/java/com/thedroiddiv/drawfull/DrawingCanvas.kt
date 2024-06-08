/**
 * Copyright (c) 2022 Divyansh Kushwaha <thedroiddiv@gmail.com>
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

package com.thedroiddiv.drawfull

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.thedroiddiv.drawfull.models.Drawing
import com.thedroiddiv.drawfull.utils.drawQuadraticBezier

@Composable
fun DrawingCanvas(
    modifier: Modifier,
    drawing: Drawing
) {
    Canvas(
        modifier = modifier
            .pointerInput(null) {
                detectDragGestures(
                    onDragStart = {
                        drawing.startDrawing(it)
                    },
                    onDrag = { change, _ ->
                        drawing.updateDrawing(change.position)
                    }
                )
            }
    ) {

        drawing.strokes.forEach { stroke ->
            when (stroke) {
                is com.thedroiddiv.drawfull.utils.DrawingStroke.FreeHand -> {
                    drawPath(
                        Path().apply { drawQuadraticBezier(stroke.points) },
                        color = stroke.color,
                        alpha = stroke.alpha,
                        style = Stroke(
                            width = stroke.width,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                is com.thedroiddiv.drawfull.utils.DrawingStroke.Polygon -> {
                    drawPath(
                        Path().apply { drawQuadraticBezier(stroke.points) },
                        color = stroke.color,
                        alpha = stroke.alpha,
                        style = Stroke(
                            width = stroke.width,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                is com.thedroiddiv.drawfull.utils.DrawingStroke.Rectangle -> {
                    drawRect(
                        color = stroke.color,
                        topLeft = stroke.topLeft,
                        size = Size(stroke.edgeWidth, stroke.edgeLength),
                        style = Stroke(
                            width = stroke.width,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                is com.thedroiddiv.drawfull.utils.DrawingStroke.Square -> {
                    drawRect(
                        color = stroke.color,
                        topLeft = stroke.topLeft,
                        size = Size(stroke.edgeLength, stroke.edgeLength),
                        style = Stroke(
                            width = stroke.width,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                is com.thedroiddiv.drawfull.utils.DrawingStroke.Circle -> {
                    drawCircle(
                        color = stroke.color,
                        radius = stroke.radius,
                        center = stroke.center,
                        style = Stroke(
                            width = stroke.width,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }
        }
    }
}