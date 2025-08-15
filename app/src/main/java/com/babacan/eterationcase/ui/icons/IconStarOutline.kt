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
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

public val Icons.IconStarOutline: ImageVector
    get() {
        if (_iconStarOutline != null) {
            return _iconStarOutline!!
        }
        _iconStarOutline = Builder(
            name = "IconStarOutline", 
            defaultWidth = 35.0.dp, 
            defaultHeight = 32.0.dp, 
            viewportWidth = 35.0f, 
            viewportHeight = 32.0f
            ).apply {
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(33.762f, 12.538f)
                    horizontalLineTo(21.358f)
                    lineTo(17.608f, 1.0f)
                    lineTo(13.858f, 12.538f)
                    horizontalLineTo(1.454f)
                    lineTo(11.55f, 19.462f)
                    lineTo(7.656f, 31.0f)
                    lineTo(17.608f, 23.788f)
                    lineTo(27.56f, 31.0f)
                    lineTo(23.666f, 19.462f)
                    lineTo(33.762f, 12.538f)
                    close()
                }
            }
            .build()
            return _iconStarOutline!!
        }

    private var _iconStarOutline: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IconStarOutline, contentDescription = null)
        }
    }
