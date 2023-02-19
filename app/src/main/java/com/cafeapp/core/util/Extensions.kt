package com.cafeapp.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

@Composable
fun Dp.dpToPx(): Int = with(LocalDensity.current) { this@dpToPx.toPx() }.roundToInt()