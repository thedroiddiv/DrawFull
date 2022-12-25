package com.dxn.imageannonator.ui.helper

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
        color: Color = Color.Green,
        width: Float = 4f,
        alpha: Float = 1f
    ) : DrawingStroke(color, width, alpha)

    open class Polygon(
        val points: SnapshotStateList<Offset>,
        color: Color = Color.Green,
        width: Float = 4f,
        alpha: Float = 1f
    ) : DrawingStroke(color, width, alpha) {
        init {
            points.add(points[0]) // add the first point at last
        }
    }

    class Square(
        val center: Offset,
        val radius: Float,
        color: Color = Color.Green,
        width: Float = 4f,
        alpha: Float = 1f
    ) : DrawingStroke(color, width, alpha)

    class Rectangle(
        val topLeft: Offset,
        val bottomRight: Offset,
        color: Color = Color.Green,
        width: Float = 4f,
        alpha: Float = 1f
    ) : DrawingStroke(color, width, alpha)

    class Circle(
        val center: Offset,
        val radius: Float,
        color: Color = Color.Green,
        width: Float = 4f,
        alpha: Float = 1f
    ) : DrawingStroke(color, width, alpha)
}