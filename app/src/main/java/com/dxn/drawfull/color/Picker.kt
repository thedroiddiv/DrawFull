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

package com.dxn.drawfull.color

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dxn.drawfull.ui.theme.DrawFullTheme

@Composable
fun ColorPicker(
    colors: List<Color>,
    selectedColor: Color,
    onColorPicked: (Color) -> Unit
) {
    FlowRow {
        colors.forEach { color ->
            val border =
                if (selectedColor == color) Color.Black else Color.Transparent
            IconButton(onClick = { onColorPicked(color) }) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(color, CircleShape)
                        .border(2.dp, border, CircleShape)

                )
            }
        }
    }
}

@Preview
@Composable
fun ColorPickerPrev() {
    DrawFullTheme {
        var selectedColor by remember { mutableStateOf(colors[0]) }
        ColorPicker(
            colors = colors,
            selectedColor = selectedColor,
            onColorPicked = { selectedColor = it })
    }
}