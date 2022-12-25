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

package com.dxn.drawfull.drawing.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Path.drawQuadraticBezier(points: List<Offset>) {
    if (points.size <= 1) return // need atLeast two points to draw path
    moveTo(points[0].x, points[0].y) // move the cursor from (0,0) to x0, y0
    var prevPoint = points[1]
    points.forEachIndexed { idx, point ->
        if (idx == 0) return@forEachIndexed
        // set middle as control point
        val controlPoint = Offset((prevPoint.x + point.x) / 2, (prevPoint.y + point.y) / 2)
        // draw a bezier curve from `prevPoint` to `point` through `controlPoint`
        quadraticBezierTo(controlPoint.x, controlPoint.y, point.x, point.y)
        prevPoint = point
    }
}

fun Path.drawLinear(points: List<Offset>) {
    if (points.size <= 1) return // need atLeast two points to draw path
    moveTo(points[0].x, points[0].y)
    points.forEach { lineTo(it.x, it.y) }
}