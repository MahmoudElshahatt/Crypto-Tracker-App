package com.plcoding.cryptotracker.crypto.presentation.model

import androidx.annotation.DrawableRes
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import com.plcoding.core.presentation.util.getDrawableIdForCoin
import com.plcoding.cryptotracker.crypto.presentation.coin_details.DataPoint
import java.text.NumberFormat
import java.util.Locale

data class CoinUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory : List<DataPoint> = emptyList(),
)


data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUI() = CoinUI(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    marketCapUsd = marketCapUsd.toDisplayableNumber(),
    priceUsd = priceUsd.toDisplayableNumber(),
    changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(symbol)
)

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
        .apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}