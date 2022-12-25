package com.dxn.imageannonator.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

// A container containing list of points through which a line is drawn
class CanvasStroke(
    val points: SnapshotStateList<Offset>,
    val color: Color = Color.Green,
    val width: Float = 4f,
    val alpha: Float = 1f
)

class Drawing {
    private val _strokes = mutableStateListOf<CanvasStroke>()
    val strokes: SnapshotStateList<CanvasStroke> get() = _strokes

    fun addNewStroke(offset: Offset) {
        val stroke = CanvasStroke(mutableStateListOf(offset))
        _strokes.add(stroke)
    }

    fun continueStroke(offset: Offset) {
        if (_strokes.isEmpty()) return
        val lastPath = _strokes[strokes.lastIndex]
        lastPath.points.add(offset)
    }

    fun clear() {
        _strokes.clear()
    }
}

@Composable
fun rememberDrawing(): Drawing {
    return remember { Drawing() }
}