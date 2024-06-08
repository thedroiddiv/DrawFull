/**
 * Copyright (c) 2022 Divyansh Kushwaha <theddroiddiv@gmail.com>
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

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow
import kotlin.math.sqrt

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