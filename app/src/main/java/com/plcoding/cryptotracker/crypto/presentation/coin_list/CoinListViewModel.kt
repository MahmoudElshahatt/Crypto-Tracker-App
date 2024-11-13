package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.util.onError
import com.plcoding.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.data_source.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.coin_details.DataPoint
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUI
import com.plcoding.cryptotracker.crypto.presentation.model.toCoinUI
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.CoinClick -> {
                selectCoin(action.coinUI)
            }
        }
    }

    private fun selectCoin(coinUI: CoinUI) {
        _state.update { it.copy(selectedCoin = coinUI) }
        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUI.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
                val dataPoints = history
                    .sortedBy { it.dateTime }
                    .map {
                        DataPoint(
                            x = it.dateTime.hour.toFloat(),
                            y = it.priceUsd.toFloat(),
                            xLabel = DateTimeFormatter
                                .ofPattern("ha\nM/d")
                                .format(it.dateTime)
                        )
                    }

                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = dataPoints
                        )
                    )
                }
            }.onError {
                _state.update { it.copy(isLoading = false) }
                _events.send(CoinListEvent.Error(it))
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUI() }
                        )
                    }
                }
                .onError {
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(it))
                }
        }
    }
}