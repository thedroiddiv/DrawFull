package com.dxn.drawfull.ui.screens.drawing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thedroiddiv.drawfull.models.DrawMode

@Composable
fun DrawModeButton(
    drawMode: DrawMode,
    selected: DrawMode,
    onSelect: (DrawMode) -> Unit,
    content: @Composable () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = if (selected::class.java == drawMode::class.java) Color.Red else Color.Transparent,
                shape = CircleShape
            ),
        onClick = { onSelect(drawMode) }
    ) { content() }
}