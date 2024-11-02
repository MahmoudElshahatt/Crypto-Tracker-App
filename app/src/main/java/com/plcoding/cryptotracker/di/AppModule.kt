package com.plcoding.cryptotracker.di

import com.plcoding.core.data.network.HttpClientFactory
import com.plcoding.cryptotracker.crypto.data.network.RemoteCoinDataSource
import com.plcoding.cryptotracker.crypto.domain.data_source.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }  //HttpClient
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()  //CoinDataSource Implementation(RemoteCoinDataSource)

    viewModelOf(::CoinListViewModel)

}