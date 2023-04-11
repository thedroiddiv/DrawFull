package com.dxn.drawfull.annot

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateObserver
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.io.ByteArrayOutputStream
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class PolygonDrawer(
    var color: Color,
    var width: Float,
    var alpha: Float,
    val vertexRadius: Float
) {
    companion object {
        private const val radius = 128f
    }

    data class Polygon(
        val vertices: List<Offset>,
        val color: Color = Color.Green,
        val width: Float,
        val alpha: Float
    )

    private val _undoList = mutableStateListOf<Polygon>()
    private val _redoList = mutableStateListOf<Polygon>()

    val strokes: SnapshotStateList<Polygon> = _undoList
    val redoList: SnapshotStateList<Polygon> = _redoList

//    private val _zoomValue = mutableStateOf(1f)
//    val zoomValue : State<Float> = _zoomValue
//
//    private val _dragOffset = mutableStateOf(Offset.Zero)
//    val dragOffset : State<Offset> = _dragOffset
//
//     fun zoom(float: Float) {
//        _zoomValue.value = maxOf(.5f, minOf(3f, float))
//    }
//
//     fun drag(offsetChange : Offset) {
//        _dragOffset.value = offsetChange
//    }


    // pair = (x,y), polygon = strokes[x], vertex = stroke[y]
    var beingDraggedVertex = Pair(-1, -1)

    /**
     * Place an n-sided polygon at with center at center
     * Calculate n-vertices based on the coordinates of the center.
     * Put the vertices in a list and create a polygon
     * add the polygon to undo-list
     */
    fun create(sides: Int, center: Offset) {
        val vertices = getVertices(radius, center, sides)
        val polygon = Polygon(vertices, color, width, alpha)
        _undoList.add(polygon)
    }

    /**
     * Check if the offset is nearby any vertices of any polygon,
     * if yes, then set isBeingDragged = (polygonIndex, vertexIndex)
     * else beingDraggedVertex = (-1,-1)
     */
    fun onDragStarted(offset: Offset) {
        _undoList.forEachIndexed { idx, polygon ->
            val vertices = polygon.vertices

            val vtx = vertices.indexOfFirst {
                val d = calculateDistance(it, offset)
                d < (2 * vertexRadius)
            }

            if (vtx != -1) {
                beingDraggedVertex = Pair(idx, vtx)
            }
        }

    }

    fun onDragEnd() {
        beingDraggedVertex = Pair(-1, -1)
    }

    /**
     * if beingDraggedVertex != (-1,-1), (x,y)
     * set the xth polygon's yth vertex = offset
     */
    fun onDragging(offset: Offset) {
        if (beingDraggedVertex.second != -1) {
            val polygonIdx = beingDraggedVertex.first
            val vertexIdx = beingDraggedVertex.second

            val polygon = _undoList[polygonIdx]
            val vertices = polygon.vertices.toMutableList().apply {
                set(vertexIdx, offset)
            }

            _undoList[polygonIdx] = polygon.copy(vertices = vertices)
        }
    }


    fun undo() {
        TODO("Not yet implemented")
    }

    fun redo() {
        TODO("Not yet implemented")
    }

    fun clear() {
        _undoList.clear()
    }

    // d = sqrt((y2-y2)^2 + (x2-x2)^2)
    private fun calculateDistance(offset1: Offset, offset2: Offset) = sqrt(
        (offset1.y - offset2.y).toDouble().pow(2.0) +
                (offset2.x - offset1.x).toDouble().pow(2.0)
    ).toFloat()


    private fun getVertices(radius: Float, center: Offset, sides: Int): MutableList<Offset> {
        val vertices = mutableListOf<Offset>()
        val x = center.x
        val y = center.y
        for (i in 0 until sides) {
            val x1 = (x + radius * cos(2 * Math.PI * i / sides)).toFloat()
            val y1 = (y + radius * sin(2 * Math.PI * i / sides)).toFloat()
            vertices.add(Offset(x1, y1))
        }
        return vertices
    }
}

fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path.toString())
}