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

// d = sqrt((y2-y2)^2 + (x2-x2)^2)
fun calculateDistance(offset1: Offset, offset2: Offset) = sqrt(
    (offset1.y - offset2.y).toDouble().pow(2.0) +
            (offset2.x - offset1.x).toDouble().pow(2.0)
).toFloat()

//
fun calculateMidPoint(offset1: Offset, offset2: Offset) = Offset(
    x = (offset1.x + offset2.x) / 2,
    y = (offset1.y + offset2.y) / 2
)

// get top left coordinates of a square/rect from two end-points of diagonal
fun getTopLeft(d1: Offset, d2: Offset): Offset {
    val x1 = d1.x
    val x2 = d2.x
    val y1 = d1.y
    val y2 = d2.y
    return if (y2 > y1) {
        if (x2 > x1) {
            Offset(x1, y1)

        } else {
            Offset(x2, y1)
        }
    } else {
        if (x2 > x1) {
            Offset(x1, y2)
        } else {
            Offset(x2, y2)
        }
    }
}

fun getTopLeftAndBottomRight(d1: Offset, d2: Offset): Pair<Offset, Offset> {
    val x1 = d1.x
    val x2 = d2.x
    val y1 = d1.y
    val y2 = d2.y
    return if (y2 > y1) {
        if (x2 > x1) {
            Pair(Offset(x1, y1), Offset(x2, y2))
        } else {
            Pair(Offset(x2, y1), Offset(x1, y2))
        }
    } else {
        if (x2 > x1) {
            Pair(Offset(x1, y2), Offset(x2, y1))
        } else {
            Pair(Offset(x2, y2), Offset(x1, y1))
        }
    }
}

fun getVertices(radius: Float, center: Offset, sides: Int): MutableList<Offset> {
    val vertices = mutableListOf<Offset>()
    val x = center.x
    val y = center.y
    for (i in 0 until sides) {
        val x1 = (x + radius * Math.cos(2 * Math.PI * i / sides)).toFloat()
        val y1 = (y + radius * Math.sin(2 * Math.PI * i / sides)).toFloat()
        vertices.add(Offset(x1, y1))
    }
    return vertices
}