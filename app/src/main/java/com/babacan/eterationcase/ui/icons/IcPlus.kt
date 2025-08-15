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

public val Icons.IcPlus: ImageVector
    get() {
        if (_icPlus != null) {
            return _icPlus!!
        }
        _icPlus = Builder(
            name = "IcPlus", 
            defaultWidth = 8.0.dp, 
            defaultHeight = 9.0.dp, 
            viewportWidth = 8.0f, 
            viewportHeight = 9.0f
            ).apply {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(2.8107f, 8.2627f)
                    verticalLineTo(0.8707f)
                    horizontalLineTo(4.8747f)
                    verticalLineTo(8.2627f)
                    horizontalLineTo(2.8107f)
                    close()
                    moveTo(0.0427f, 5.5427f)
                    verticalLineTo(3.5907f)
                    horizontalLineTo(7.6587f)
                    verticalLineTo(5.5427f)
                    horizontalLineTo(0.0427f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF2A59FE)), stroke = null, fillAlpha = 0.3f,
                        strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(2.8107f, 8.2627f)
                    verticalLineTo(0.8707f)
                    horizontalLineTo(4.8747f)
                    verticalLineTo(8.2627f)
                    horizontalLineTo(2.8107f)
                    close()
                    moveTo(0.0427f, 5.5427f)
                    verticalLineTo(3.5907f)
                    horizontalLineTo(7.6587f)
                    verticalLineTo(5.5427f)
                    horizontalLineTo(0.0427f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.35f,
                        strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(2.8107f, 8.2627f)
                    verticalLineTo(0.8707f)
                    horizontalLineTo(4.8747f)
                    verticalLineTo(8.2627f)
                    horizontalLineTo(2.8107f)
                    close()
                    moveTo(0.0427f, 5.5427f)
                    verticalLineTo(3.5907f)
                    horizontalLineTo(7.6587f)
                    verticalLineTo(5.5427f)
                    horizontalLineTo(0.0427f)
                    close()
                }
            }
            .build()
            return _icPlus!!
        }

    private var _icPlus: ImageVector? = null

    @Preview
    @Composable
    private fun Preview() {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(imageVector = Icons.IcPlus, contentDescription = null)
        }
    }
