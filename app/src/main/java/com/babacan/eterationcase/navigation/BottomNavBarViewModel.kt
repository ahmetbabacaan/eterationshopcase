package com.babacan.eterationcase.navigation

import androidx.lifecycle.viewModelScope
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.presentation.base.BaseViewModel
import com.babacan.eterationcase.presentation.base.Effect
import com.babacan.eterationcase.presentation.base.Event
import com.babacan.eterationcase.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavBarViewModel @Inject constructor(
    shopDAO: ShopDAO
) : BaseViewModel<BottomNavBarEvent, BottomNavBarState, BottomNavBarEffect> () {

    override fun setInitialState(): BottomNavBarState {
        return BottomNavBarState()
    }

    override fun handleEvents(event: BottomNavBarEvent) {}

    init {
        viewModelScope.launch(Dispatchers.IO) {
            shopDAO.getDistinctItemCountFlow().collect { size ->
                setState { copy(basketProductsSize = size) }
            }
        }
    }
}



data class BottomNavBarState(
    val basketProductsSize: Int = 0,
) : State

sealed interface BottomNavBarEvent : Event
sealed interface BottomNavBarEffect : Effect