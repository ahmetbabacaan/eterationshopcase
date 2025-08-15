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

public val Icons.IconBasketOutline: ImageVector
    get() {
        if (_iconBasketOutline != null) {
            return _iconBasketOutline!!
        }
        _iconBasketOutline = Builder(
            name = "IconBasketOutline", 
            defaultWidth = 37.0.dp, 
            defaultHeight = 32.0.dp, 
            viewportWidth = 37.0f, 
            viewportHeight = 32.0f
            ).apply {
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(2.984f, 11.506f)
                    curveTo(2.542f, 11.506f, 2.119f, 11.68f, 1.805f, 11.99f)
                    curveTo(1.491f, 12.301f, 1.313f, 12.723f, 1.309f, 13.164f)
                    curveTo(1.307f, 13.317f, 1.33f, 13.469f, 1.375f, 13.616f)
                    lineTo(5.618f, 28.579f)
                    curveTo(5.815f, 29.278f, 6.235f, 29.893f, 6.815f, 30.33f)
                    curveTo(7.395f, 30.767f, 8.102f, 31.003f, 8.828f, 31.0f)
                    horizontalLineTo(27.936f)
                    curveTo(28.663f, 31.001f, 29.371f, 30.765f, 29.953f, 30.328f)
                    curveTo(30.535f, 29.891f, 30.959f, 29.277f, 31.161f, 28.579f)
                    lineTo(35.405f, 13.616f)
                    lineTo(35.454f, 13.164f)
                    curveTo(35.45f, 12.723f, 35.272f, 12.301f, 34.958f, 11.99f)
                    curveTo(34.645f, 11.68f, 34.221f, 11.506f, 33.78f, 11.506f)
                    horizontalLineTo(2.984f)
                    close()
                    moveTo(18.851f, 24.645f)
                    curveTo(18.169f, 24.641f, 17.503f, 24.436f, 16.937f, 24.054f)
                    curveTo(16.372f, 23.673f, 15.932f, 23.132f, 15.673f, 22.501f)
                    curveTo(15.415f, 21.87f, 15.349f, 21.176f, 15.484f, 20.507f)
                    curveTo(15.619f, 19.839f, 15.949f, 19.225f, 16.433f, 18.744f)
                    curveTo(16.916f, 18.263f, 17.532f, 17.935f, 18.201f, 17.804f)
                    curveTo(18.87f, 17.672f, 19.564f, 17.741f, 20.194f, 18.003f)
                    curveTo(20.824f, 18.265f, 21.362f, 18.707f, 21.741f, 19.275f)
                    curveTo(22.119f, 19.842f, 22.321f, 20.509f, 22.321f, 21.191f)
                    curveTo(22.317f, 22.109f, 21.949f, 22.987f, 21.299f, 23.635f)
                    curveTo(20.649f, 24.282f, 19.769f, 24.645f, 18.851f, 24.645f)
                    verticalLineTo(24.645f)
                    close()
                }
                path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                        strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(10.502f, 11.506f)
                    lineTo(18.382f, 1.0f)
                    lineTo(26.261f, 11.506f)
                }
            }
            .build()
            return _iconBasketOutline!!
        }

    private var _iconBasketOutline: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IconBasketOutline, contentDescription = null)
        }
    }
