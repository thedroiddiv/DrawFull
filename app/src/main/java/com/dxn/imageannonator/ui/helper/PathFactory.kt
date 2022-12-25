package com.dxn.imageannonator.ui.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun createPath(points: List<Offset>) = Path().apply {
    if (points.size <= 1) return@apply // need atLeast two points to draw path
    moveTo(points[0].x, points[0].y)
    points.forEach { lineTo(it.x, it.y) }
}

fun createBezierPath(points: List<Offset>) = Path().apply {
    if (points.size <= 1) return@apply // need atLeast two points to draw path
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