package com.babacan.eterationcase.presentation.basket

import androidx.lifecycle.viewModelScope
import com.babacan.eterationcase.common.priceToDouble
import com.babacan.eterationcase.common.toPrice
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.presentation.base.BaseViewModel
import com.babacan.eterationcase.presentation.base.Effect
import com.babacan.eterationcase.presentation.base.Event
import com.babacan.eterationcase.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val shopDAO: ShopDAO,
) : BaseViewModel<BasketEvent, BasketState, BasketEffect>() {

    private var cartJob: Job? = null

    override fun setInitialState(): BasketState {
        return BasketState()
    }

    override fun handleEvents(event: BasketEvent) {
        when (event) {
            BasketEvent.OnBackClicked -> {
                setEffect { BasketEffect.NavigateBack }
            }

            is BasketEvent.OnDecreaseQuantity -> {
                viewModelScope.launch(Dispatchers.IO) {
                    shopDAO.decreaseQuantity(event.product.id)
                }
            }

            is BasketEvent.OnIncreaseQuantity -> {
                viewModelScope.launch(Dispatchers.IO) {
                    shopDAO.insertOrIncrementQuantity(
                        id = event.product.id,
                        name = event.product.name,
                        price = event.product.price,
                        image = event.product.image,
                        description = event.product.description,
                        brand = event.product.brand,
                        model = event.product.model,
                        createdAt = event.product.createdAt,
                        isFavorite = event.product.isFavorite
                    )
                }
            }

            BasketEvent.OnCompleteClick -> {
                stopCollectingCart()
                viewModelScope.launch(Dispatchers.IO) {
                    shopDAO.clearCart()
                    setEffect { BasketEffect.NavigateToHome }
                }
            }
        }
    }

    init {
        getBasket()
    }

    private fun getBasket() {
        cartJob?.cancel()
        cartJob = viewModelScope.launch(Dispatchers.IO) {
            shopDAO.getAllCartItems().collect { products ->
                setState {
                    copy(products = products.toImmutableList(), isLoading = false)
                }
            }
        }
    }

    fun stopCollectingCart() {
        cartJob?.cancel()
        cartJob = null
    }
}

sealed interface BasketEvent : Event {
    data object OnBackClicked : BasketEvent
    data object OnCompleteClick : BasketEvent
    data class OnIncreaseQuantity(val product: ShopProduct) : BasketEvent
    data class OnDecreaseQuantity(val product: ShopProduct) : BasketEvent
}

data class BasketState(
    val isLoading: Boolean = false,
    val products: ImmutableList<ShopProduct> = persistentListOf()
) : State {
    val totalPrice: String
        get() = products.sumOf { it.price.priceToDouble() * it.quantity }.toPrice()

    val showEmptyBasketText: Boolean
        get() = products.isEmpty()
}

sealed interface BasketEffect : Effect {
    data object NavigateBack : BasketEffect
    data object NavigateToHome : BasketEffect
}