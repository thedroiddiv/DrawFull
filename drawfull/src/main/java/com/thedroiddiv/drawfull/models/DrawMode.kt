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


package com.thedroiddiv.drawfull.models

sealed class DrawMode {
    data object FREE_HAND : DrawMode()
    data object SQUARE : DrawMode()
    data object CIRCLE : DrawMode()
    class POLYGON(val sides: Int) : DrawMode()
    data object RECTANGLE : DrawMode()
}