package com.babacan.eterationcase.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

public val Icons.IconStarSelected: ImageVector
    get() {
        if (_iconStarSelected != null) {
            return _iconStarSelected!!
        }
        _iconStarSelected = Builder(
            name = "IconStarSelected", 
            defaultWidth = 24.0.dp, 
            defaultHeight = 22.0.dp, 
            viewportWidth = 24.0f, 
            viewportHeight = 22.0f
            ).apply {
                path(fill = SolidColor(Color(0xFFFFB800)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(12.0f, 0.0f)
                    lineTo(14.694f, 8.292f)
                    horizontalLineTo(23.413f)
                    lineTo(16.359f, 13.416f)
                    lineTo(19.053f, 21.708f)
                    lineTo(12.0f, 16.584f)
                    lineTo(4.947f, 21.708f)
                    lineTo(7.641f, 13.416f)
                    lineTo(0.587f, 8.292f)
                    horizontalLineTo(9.306f)
                    lineTo(12.0f, 0.0f)
                    close()
                }
            }
            .build()
            return _iconStarSelected!!
        }

    private var _iconStarSelected: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IconStarSelected, contentDescription = null)
        }
    }
