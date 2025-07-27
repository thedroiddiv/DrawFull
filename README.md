# 🎨 DrawFull

**DrawFull** is a lightweight, powerful Jetpack Compose drawing library for Android. It allows users to draw free-form paths and geometric shapes (Circle, Square, Rectangle, Polygon), and provides built-in support for undo, redo, and clear operations, all with simple APIs.

## ✨ Features

- 🖌️ Free-hand drawing with bezier smoothing  
- 🔷 Shape drawing: Circle, Rectangle, Square, Polygon (with customizable sides)  
- ↩️ Undo & Redo support  
- 🧽 Clear canvas  
- 🎚️ Customizable stroke color, width, and alpha  
- 🎨 Built-in color picker  
- 🤖 Jetpack Compose friendly  

## 📦 Installation

> Coming soon on MavenCentral / JitPack  
For now, copy the `Drawing`, `DrawingStroke`, and related components into your project.

## 🚀 Quick Start

### 1. Create a `Drawing` instance

```kotlin
val drawing = rememberDrawing()
```

### 2. Use `DrawingCanvas` to render the drawing surface

```kotlin
DrawingCanvas(
    modifier = Modifier.fillMaxSize(),
    drawing = drawing
)
```

### 3. Add controls (clear, undo, redo, color, shapes)

```kotlin
Row {
    IconButton(onClick = { drawing.clear() }) { /* clear icon */ }
    IconButton(onClick = { drawing.undo() }) { /* undo icon */ }
    IconButton(onClick = { drawing.redo() }) { /* redo icon */ }
    // ColorPicker and Shape selector
}
```

### 4. Full Sample

```kotlin
@Composable
fun DrawingScreen() {
    val drawing = rememberDrawing()

    Column {
        DrawingCanvas(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            drawing = drawing
        )

        // Tool bar with color picker, shape selector
        Row {
            IconButton(onClick = { drawing.clear() }) { /* Clear */ }
            IconButton(onClick = { drawing.undo() }) { /* Undo */ }
            IconButton(onClick = { drawing.redo() }) { /* Redo */ }

            DrawModeSelector(
                selected = drawing.drawMode.value,
                onSelect = { drawing.setDrawMode(it) },
                polygonSides = 5
            )

            ColorPicker(
                colors = colors,
                selectedColor = drawing.color,
                onColorPicked = { drawing.setColor(it) }
            )
        }
    }
}
```

## 🛠️ Supported Drawing Modes

```kotlin
sealed class DrawMode {
    object FREE_HAND
    object CIRCLE
    object RECTANGLE
    object SQUARE
    data class POLYGON(val sides: Int)
}
```

Switch mode with:

```kotlin
drawing.setDrawMode(DrawMode.CIRCLE)
```

## 📘 API Summary

| Function         | Description                             |
|------------------|-----------------------------------------|
| `undo()`         | Undo last stroke                        |
| `redo()`         | Redo previously undone stroke           |
| `clear()`        | Clear all strokes                       |
| `setColor()`     | Set stroke color                        |
| `setWidth()`     | Set stroke width                        |
| `setAlpha()`     | Set stroke transparency                 |
| `setDrawMode()`  | Set current drawing mode                |

## 📄 License

GNU GENERAL PUBLIC LICENSE
Copyright © 2025 [@thedroiddiv](https://github.com/thedroiddiv)
