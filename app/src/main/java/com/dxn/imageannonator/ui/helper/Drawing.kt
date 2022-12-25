package com.dxn.imageannonator.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset

class Drawing {

    private val _undoList = mutableStateListOf<DrawingStroke>()
    val strokes: SnapshotStateList<DrawingStroke> get() = _undoList
    private val _redoList = mutableStateListOf<DrawingStroke>()

    fun addNewFreeHandStroke(offset: Offset) {
        val stroke = DrawingStroke.FreeHand(mutableStateListOf(offset))
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
        _undoList.add(DrawingStroke.Square(center, radius))
    }

    fun addCircle(center: Offset, radius: Float) {
        _undoList.add(DrawingStroke.Circle(center, radius))
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
}

@Composable
fun rememberDrawing(): Drawing {
    return remember { Drawing() }
}