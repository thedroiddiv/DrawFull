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

package com.dxn.drawfull.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.dxn.drawfull.ui.helper.Drawing
import com.dxn.drawfull.ui.helper.DrawingStroke
import com.dxn.drawfull.ui.helper.drawQuadraticBezier

@Composable
fun FreeHandCanvas(
    modifier: Modifier,
    drawing: Drawing
) {
    Canvas(
        modifier = modifier
            .pointerInput(null) {
                detectDragGestures(
                    onDragStart = { drawing.addNewFreeHandStroke(it) },
                    onDrag = { change, _ ->
                        drawing.continueFreeHandStroke(change.position)
                    }
                )
            }
    ) {

        drawing.strokes.forEach { stroke ->
            when (stroke) {
                is DrawingStroke.FreeHand -> {
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

                is DrawingStroke.Polygon -> {
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

                is DrawingStroke.Square -> {
                    val topLeft = Offset(
                        (stroke.center.x - stroke.radius),
                        (stroke.center.y - stroke.radius)
                    )
                    drawRect(
                        color = stroke.color,
                        topLeft = topLeft,
                        size = Size(stroke.radius * 2, stroke.radius * 2),
                        style = Stroke(
                            width = stroke.width,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                is DrawingStroke.Circle -> {
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