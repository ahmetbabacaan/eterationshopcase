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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.StrokeCap.Companion.Round as strokeCapRound
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round as strokeJoinRound

public val Icons.IconHomeOutline: ImageVector
    get() {
        if (_iconHomeOutline != null) {
            return _iconHomeOutline!!
        }
        _iconHomeOutline = Builder(
            name = "IconHomeOutline", 
            defaultWidth = 35.0.dp, 
            defaultHeight = 32.0.dp, 
            viewportWidth = 35.0f, 
            viewportHeight = 32.0f
            ).apply {
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
                        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(4.462f, 12.826f)
                    verticalLineTo(29.846f)
                    curveTo(4.462f, 30.152f, 4.583f, 30.446f, 4.8f, 30.662f)
                    curveTo(5.016f, 30.878f, 5.31f, 31.0f, 5.616f, 31.0f)
                    horizontalLineTo(12.539f)
                    verticalLineTo(21.192f)
                    curveTo(12.539f, 20.733f, 12.721f, 20.293f, 13.046f, 19.968f)
                    curveTo(13.37f, 19.643f, 13.811f, 19.461f, 14.27f, 19.461f)
                    horizontalLineTo(20.039f)
                    curveTo(20.498f, 19.461f, 20.939f, 19.643f, 21.263f, 19.968f)
                    curveTo(21.588f, 20.293f, 21.77f, 20.733f, 21.77f, 21.192f)
                    verticalLineTo(31.0f)
                    horizontalLineTo(28.694f)
                    curveTo(29.0f, 31.0f, 29.293f, 30.878f, 29.509f, 30.662f)
                    curveTo(29.726f, 30.446f, 29.847f, 30.152f, 29.847f, 29.846f)
                    verticalLineTo(12.826f)
                }
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
                        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(33.309f, 15.999f)
                    lineTo(17.94f, 1.287f)
                    curveTo(17.579f, 0.906f, 16.736f, 0.902f, 16.369f, 1.287f)
                    lineTo(1.0f, 15.999f)
                }
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
                        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(27.54f, 10.446f)
                    verticalLineTo(2.153f)
                    horizontalLineTo(24.078f)
                    verticalLineTo(7.129f)
                }
            }
            .build()
            return _iconHomeOutline!!
        }

    private var _iconHomeOutline: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IconHomeOutline, contentDescription = null)
        }
    }
