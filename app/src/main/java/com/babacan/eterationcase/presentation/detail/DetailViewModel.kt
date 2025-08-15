package com.babacan.eterationcase.presentation.detail

import androidx.lifecycle.viewModelScope
import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.use_cases.GetProductByIdUseCaseImpl
import com.babacan.eterationcase.presentation.base.BaseViewModel
import com.babacan.eterationcase.presentation.base.Effect
import com.babacan.eterationcase.presentation.base.Event
import com.babacan.eterationcase.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCaseImpl: GetProductByIdUseCaseImpl,
    private val shopDAO: ShopDAO,
) : BaseViewModel<DetailEvent, DetailState, DetailEffect>() {
    override fun setInitialState(): DetailState {
        return DetailState()
    }

    override fun handleEvents(event: DetailEvent) {
        when (event) {
            DetailEvent.OnBackClicked -> {
                setEffect { DetailEffect.NavigateBack }
            }

            is DetailEvent.SetProductId -> {
                setState { copy(productId = event.productId) }
                getProductDetails(event.productId)
            }

            DetailEvent.OnAddToCart -> {
                getCurrentState().product?.let { product ->
                    viewModelScope.launch(Dispatchers.IO) {
                        shopDAO.insertOrIncrementQuantity(
                            id = product.id,
                            name = product.name,
                            price = product.price,
                            image = product.image,
                            description = product.description,
                            brand = product.brand,
                            model = product.model,
                            createdAt = product.createdAt,
                            isFavorite = product.isFavorite
                        )
                    }
                }
            }

            DetailEvent.OnToggleFavorite -> {
                getCurrentState().product?.let { product ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val favorite = Favorite(
                            id = product.id,
                            image = product.image,
                            name = product.name,
                            price = product.price,
                        )
                        shopDAO.toggleFavorite(favorite)
                    }
                }
            }
        }
    }


    private fun getProductDetails(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductByIdUseCaseImpl(productId).collect { result ->
                setState { copy(isLoading = result == Result.Loading) }
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success<ShopProduct> -> {
                        setState { copy(product = result.data) }
                    }
                }
            }
        }


    }
}

sealed interface DetailEvent : Event {
    data object OnBackClicked : DetailEvent
    data object OnToggleFavorite : DetailEvent
    data object OnAddToCart : DetailEvent
    data class SetProductId(val productId: String) : DetailEvent
}

data class DetailState(
    val isLoading: Boolean = false,
    val product: ShopProduct? = null,
    val productId: String? = null
) : State

sealed interface DetailEffect : Effect {
    data object NavigateBack : DetailEffect
}