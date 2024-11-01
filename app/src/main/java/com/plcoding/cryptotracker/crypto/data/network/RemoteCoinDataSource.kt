package com.plcoding.cryptotracker.crypto.data.network

import com.plcoding.core.data.network.constructUrl
import com.plcoding.core.data.network.safeCallApi
import com.plcoding.core.domain.util.NetworkError
import com.plcoding.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.data_source.CoinDataSource
import io.ktor.client.HttpClient
import com.plcoding.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import io.ktor.client.request.get


class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCallApi<CoinsResponseDto> {
            httpClient.get(urlString = constructUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}