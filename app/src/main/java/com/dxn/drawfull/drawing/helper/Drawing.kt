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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

class Drawing(
    private var color: Color,
    private var width: Float,
    private var alpha: Float
) {

    private val _undoList = mutableStateListOf<DrawingStroke>()
    val strokes: SnapshotStateList<DrawingStroke> get() = _undoList
    private val _redoList = mutableStateListOf<DrawingStroke>()

    fun addNewFreeHandStroke(offset: Offset) {
        val stroke = DrawingStroke.FreeHand(mutableStateListOf(offset), color, width, alpha)
        _undoList.add(stroke)
    }

    fun continueFreeHandStroke(offset: Offset) {
        if (_undoList.isEmpty()) return
        val lastStroke = _undoList[_undoList.lastIndex]
        if (lastStroke is DrawingStroke.FreeHand) {
            lastStroke.points.add(offset)
        }
    }

    fun addSquare(center: Offset, radius: Float) {
        _undoList.add(DrawingStroke.Square(center, radius, color, width, alpha))
    }

    fun addCircle(center: Offset, radius: Float) {
        _undoList.add(DrawingStroke.Circle(center, radius, color, width, alpha))
    }

    fun undo() {
        if (_undoList.isEmpty()) return
        val removed = _undoList.removeLast()
        _redoList.add(removed)
    }

    fun redo() {
        if (_redoList.isEmpty()) return
        val removed = _redoList.removeLast()
        _undoList.add(removed)
    }

    fun clear() {
        _undoList.clear()
        _redoList.clear()
    }

    fun setColor(color: Color) = run { this.color = color }
    fun setWidth(width: Float) = run { this.width = width }
    fun setAlpha(alpha: Float) = run { this.alpha = alpha }
}

@Composable
fun rememberDrawing(
    color: Color = Color.Red,
    width: Float = 4f,
    alpha: Float = 1f
): Drawing {
    return remember { Drawing(color, width, alpha) }
}