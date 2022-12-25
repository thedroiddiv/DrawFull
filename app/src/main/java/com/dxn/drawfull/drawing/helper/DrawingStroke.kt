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

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

sealed class DrawingStroke(
    val color: Color,
    val width: Float,
    val alpha: Float
) {
    class FreeHand(
        val points: SnapshotStateList<Offset>,
        color: Color,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha)

    open class Polygon(
        val points: SnapshotStateList<Offset>,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha) {
        init {
            points.add(points[0]) // add the first point at last
        }
    }

    class Square(
        val center: Offset,
        val radius: Float,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha)

    class Rectangle(
        val topLeft: Offset,
        val bottomRight: Offset,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha)

    class Circle(
        val center: Offset,
        val radius: Float,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha)
}