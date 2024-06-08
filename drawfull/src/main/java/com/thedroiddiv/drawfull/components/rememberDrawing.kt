/**
 * Copyright (c) 2022 Divyansh Kushwaha <thedroiddiv@gmail.com>
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

package com.thedroiddiv.drawfull.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.thedroiddiv.drawfull.models.DrawMode
import com.thedroiddiv.drawfull.models.Drawing

@Composable
fun rememberDrawing(
    color: Color = Color.Red,
    width: Float = 4f,
    alpha: Float = 1f,
    drawMode: DrawMode = DrawMode.FREE_HAND
): Drawing {
    return remember { Drawing(color, width, alpha, drawMode) }
}