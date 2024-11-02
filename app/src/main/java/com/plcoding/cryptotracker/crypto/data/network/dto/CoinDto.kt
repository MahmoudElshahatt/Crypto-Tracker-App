package com.plcoding.cryptotracker.crypto.data.network.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class CoinDto(
    @SerialName("changePercent24Hr")
    val changePercent24Hr: Double = 0.0, // -0.8101417214350335
    @SerialName("id")
    val id: String = "", // bitcoin
    @SerialName("marketCapUsd")
    val marketCapUsd: Double = 0.0, // 119150835874.4699281625807300
    @SerialName("name")
    val name: String = "", // Bitcoin
    @SerialName("priceUsd")
    val priceUsd: Double =0.0, // 6929.8217756835584756
    @SerialName("rank")
    val rank: Int = 0, // 1
    @SerialName("supply")
    val supply: String? = "", // 17193925.0000000000000000
    @SerialName("symbol")
    val symbol: String = "", // BTC
    @SerialName("volumeUsd24Hr")
    val volumeUsd24Hr: String = "", // 2927959461.1750323310959460
    @SerialName("vwap24Hr")
    val vwap24Hr: String? = "" // 7175.0663247679233209
)