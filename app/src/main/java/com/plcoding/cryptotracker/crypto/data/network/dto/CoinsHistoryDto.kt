package com.plcoding.cryptotracker.crypto.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsHistoryDto(
    val data:List<CoinPriceDto>
)