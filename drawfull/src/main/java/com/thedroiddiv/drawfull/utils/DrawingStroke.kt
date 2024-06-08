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

package com.thedroiddiv.drawfull.utils

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt


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
        val points: MutableList<Offset>,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha) {
        init {
            points.add(points[0]) // add the first point at last
        }
    }

    class Square(
        val d1: Offset,
        val d2: Offset,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha) {
        val topLeft = getTopLeft(d1, d2)
        val edgeLength
            get() = run {
                val diagonal = calculateDistance(d1, d2)
                diagonal / sqrt(2f)
            }
    }

    class Rectangle(
        val d1: Offset,
        val d2: Offset,
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha) {
        private val topLeftAndBottomRight = getTopLeftAndBottomRight(d1, d2)
        val topLeft get() = topLeftAndBottomRight.first
        val bottomRight get() = topLeftAndBottomRight.second
        val edgeWidth get() = bottomRight.x - topLeft.x
        val edgeLength get() = bottomRight.y - topLeft.y
    }

    class Circle(
        val poc1: Offset, // point on circumference 1
        val poc2: Offset, // point on circumference 2
        color: Color = Color.Green,
        width: Float,
        alpha: Float
    ) : DrawingStroke(color, width, alpha) {
        val radius get() = calculateDistance(poc1, poc2) / 2 // diameter/2
        val center
            get() = calculateMidPoint(
                poc1,
                poc2
            ) // center is the mid of line joining two opposite points on circumference
    }
}

