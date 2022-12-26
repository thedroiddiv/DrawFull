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

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

class Drawing(
    private var _color: Color,
    private var _width: Float,
    private var _alpha: Float,
    drawMode: DrawMode
) {

    private val _undoList = mutableStateListOf<DrawingStroke>()
    val strokes: SnapshotStateList<DrawingStroke> get() = _undoList
    private val _redoList = mutableStateListOf<DrawingStroke>()
    private val _drawMode = mutableStateOf(drawMode)

    val color get() = _color
    val width get() = _width
    val alpha get() = _alpha
    val drawMode: State<DrawMode> get() = _drawMode

    fun startDrawing(offset: Offset) {
        val stroke = when (_drawMode.value) {
            DrawMode.CIRCLE -> DrawingStroke.Circle(offset, offset, color, width, alpha)
            DrawMode.SQUARE -> DrawingStroke.Square(offset, offset, color, width, alpha)
            DrawMode.RECTANGLE -> DrawingStroke.Rectangle(offset, offset, color, width, alpha)
            DrawMode.POLYGON -> DrawingStroke.Rectangle(offset, offset, color, width, alpha)
            DrawMode.FREE_HAND -> DrawingStroke.FreeHand(
                mutableStateListOf(offset),
                _color,
                _width,
                _alpha
            )
        }
        _undoList.add(stroke)
    }

    fun updateDrawing(offset: Offset) {
        if (_undoList.isEmpty()) return
        when (val lastStroke = _undoList.last()) {
            is DrawingStroke.Circle -> {
                val newCircle = DrawingStroke.Circle(lastStroke.poc1, offset, color, width, alpha)
                _undoList.removeLast()
                _undoList.add(newCircle)
            }
            is DrawingStroke.FreeHand -> {
                lastStroke.points.add(offset)
            }
            is DrawingStroke.Polygon -> {

            }
            is DrawingStroke.Rectangle -> {
                val newSquare = DrawingStroke.Rectangle(lastStroke.d1, offset, color, width, alpha)
                _undoList.removeLast()
                _undoList.add(newSquare)
            }
            is DrawingStroke.Square -> {
                val newSquare = DrawingStroke.Square(lastStroke.d1, offset, color, width, alpha)
                _undoList.removeLast()
                _undoList.add(newSquare)
            }
        }
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

    fun setColor(color: Color) = run { this._color = color }
    fun setWidth(width: Float) = run { this._width = width }
    fun setAlpha(alpha: Float) = run { this._alpha = alpha }
    fun setDrawMode(drawMode: DrawMode) = run { this._drawMode.value = drawMode }
}

@Composable
fun rememberDrawing(
    color: Color = Color.Red,
    width: Float = 4f,
    alpha: Float = 1f,
    drawMode: DrawMode = DrawMode.FREE_HAND
): Drawing {
    return remember { Drawing(color, width, alpha, drawMode) }
}