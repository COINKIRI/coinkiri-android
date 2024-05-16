package com.cokiri.coinkiri.presentation.price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cokiri.coinkiri.domain.model.Coin
import com.cokiri.coinkiri.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
) : ViewModel() {

    private val _coinList = MutableStateFlow<List<Coin>>(emptyList())      // 코인 데이터를 담는 StateFlow
    val coinList: StateFlow<List<Coin>> = _coinList

    private fun loadCoins() {
        viewModelScope.launch {
            val coins = getCoinsUseCase()
            _coinList.value = coins
        }
    }
}