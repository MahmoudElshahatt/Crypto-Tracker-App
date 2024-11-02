package com.plcoding.cryptotracker.crypto.presentation.coin_list

import com.plcoding.cryptotracker.crypto.presentation.model.CoinUI

sealed interface CoinListAction {
    data class CoinClick(val coinUI: CoinUI) : CoinListAction
}