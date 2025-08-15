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
import androidx.compose.ui.graphics.StrokeCap.Companion.Round as strokeCapRound
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round as strokeJoinRound

public val Icons.IconPersonOutline: ImageVector
    get() {
        if (_iconPersonOutline != null) {
            return _iconPersonOutline!!
        }
        _iconPersonOutline = Builder(
            name = "IconPersonOutline", 
            defaultWidth = 31.0.dp, 
            defaultHeight = 32.0.dp, 
            viewportWidth = 31.0f, 
            viewportHeight = 32.0f
            ).apply {
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = strokeCapRound, strokeLineJoin =
                        strokeJoinRound, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(21.954f, 7.923f)
                    curveTo(21.672f, 11.736f, 18.781f, 14.846f, 15.608f, 14.846f)
                    curveTo(12.435f, 14.846f, 9.54f, 11.736f, 9.262f, 7.923f)
                    curveTo(8.974f, 3.957f, 11.786f, 1.0f, 15.608f, 1.0f)
                    curveTo(19.43f, 1.0f, 22.243f, 4.029f, 21.954f, 7.923f)
                    close()
                }
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(15.608f, 19.462f)
                    curveTo(9.334f, 19.462f, 2.966f, 22.923f, 1.788f, 29.457f)
                    curveTo(1.646f, 30.244f, 2.092f, 31.0f, 2.916f, 31.0f)
                    horizontalLineTo(28.3f)
                    curveTo(29.125f, 31.0f, 29.571f, 30.244f, 29.429f, 29.457f)
                    curveTo(28.25f, 22.923f, 21.882f, 19.462f, 15.608f, 19.462f)
                    verticalLineTo(19.462f)
                    close()
                }
            }
            .build()
            return _iconPersonOutline!!
        }

    private var _iconPersonOutline: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IconPersonOutline, contentDescription = null)
        }
    }
