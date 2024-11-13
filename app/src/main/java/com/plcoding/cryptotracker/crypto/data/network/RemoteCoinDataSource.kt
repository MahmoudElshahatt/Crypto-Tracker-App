package com.plcoding.cryptotracker.crypto.data.network

import com.plcoding.core.data.network.constructUrl
import com.plcoding.core.data.network.safeCallApi
import com.plcoding.core.domain.util.NetworkError
import com.plcoding.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import com.plcoding.cryptotracker.crypto.domain.data_source.CoinDataSource
import io.ktor.client.HttpClient
import com.plcoding.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.data.mappers.toCoinPrice
import com.plcoding.cryptotracker.crypto.data.network.dto.CoinsHistoryDto
import com.plcoding.cryptotracker.crypto.domain.model.CoinPrice
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime


class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins() = safeCallApi<CoinsResponseDto> {
        httpClient.get(urlString = constructUrl("/assets"))
    }.map { response ->
        response.data.map { it.toCoin() }
    }


    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCallApi<CoinsHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}
