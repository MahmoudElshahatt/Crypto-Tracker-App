package com.plcoding.cryptotracker.crypto.presentation.coin_details

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class ChartStyle(
    val chartLineColor: Color,
    val unSelectedColor: Color,
    val selectedColor: Color,
    val helperLinesThicknessPX: Float,
    val axisLinesThicknessPX: Float,
    val labelFontSize: TextUnit,
    val minYLabelSpacing: Dp,
    val verticalPadding:Dp,
    val horizontalPadding: Dp,
    val xAxisLabelSpacing: Dp,
)
