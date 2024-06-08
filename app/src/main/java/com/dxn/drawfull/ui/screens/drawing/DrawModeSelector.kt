package com.dxn.drawfull.ui.screens.drawing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dxn.drawfull.R
import com.thedroiddiv.drawfull.models.DrawMode

@Composable
fun DrawModeSelector(
    modifier: Modifier,
    selected: DrawMode,
    polygonSides: Int,
    onSelect: (DrawMode) -> Unit
) {
    Row(
        modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .horizontalScroll(rememberScrollState())
    ) {
        DrawModeButton(drawMode = DrawMode.FREE_HAND, selected = selected, onSelect = onSelect) {
            Icon(
                painter = painterResource(id = R.drawable.ic_free_hand),
                contentDescription = "free hand drawing",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        DrawModeButton(
            drawMode = DrawMode.POLYGON(polygonSides),
            selected = selected,
            onSelect = onSelect
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_polygon),
                contentDescription = "free hand drawing",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        DrawModeButton(drawMode = DrawMode.CIRCLE, selected = selected, onSelect = onSelect) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
            )
        }
        DrawModeButton(drawMode = DrawMode.SQUARE, selected = selected, onSelect = onSelect) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary)
            )
        }
        DrawModeButton(drawMode = DrawMode.RECTANGLE, selected = selected, onSelect = onSelect) {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(16.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}