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

public val Icons.IcMinus: ImageVector
    get() {
        if (_icMinus != null) {
            return _icMinus!!
        }
        _icMinus = Builder(
            name = "IcMinus", 
            defaultWidth = 5.0.dp, 
            defaultHeight = 3.0.dp, 
            viewportWidth = 5.0f, 
            viewportHeight = 3.0f
            ).apply {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(0.1881f, 2.6007f)
                    verticalLineTo(0.8507f)
                    horizontalLineTo(4.1501f)
                    verticalLineTo(2.6007f)
                    horizontalLineTo(0.1881f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF2A59FE)), stroke = null, fillAlpha = 0.3f,
                        strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(0.1881f, 2.6007f)
                    verticalLineTo(0.8507f)
                    horizontalLineTo(4.1501f)
                    verticalLineTo(2.6007f)
                    horizontalLineTo(0.1881f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.35f,
                        strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(0.1881f, 2.6007f)
                    verticalLineTo(0.8507f)
                    horizontalLineTo(4.1501f)
                    verticalLineTo(2.6007f)
                    horizontalLineTo(0.1881f)
                    close()
                }
            }
            .build()
            return _icMinus!!
        }

    private var _icMinus: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IcMinus, contentDescription = null)
        }
    }
