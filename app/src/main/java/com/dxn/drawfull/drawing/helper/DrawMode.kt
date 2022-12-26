package com.dxn.drawfull.drawing.helper

sealed class DrawMode {
    object FREE_HAND : DrawMode()
    object SQUARE : DrawMode()
    object CIRCLE : DrawMode()
    class POLYGON(val sides: Int) : DrawMode()
    object RECTANGLE : DrawMode()
}