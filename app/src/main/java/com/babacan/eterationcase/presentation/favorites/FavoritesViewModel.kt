package com.babacan.eterationcase.presentation.favorites

import androidx.lifecycle.viewModelScope
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.Favorite
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val shopDAO: ShopDAO,
) :
    BaseViewModel<FavoritesEvent, FavoritesState, FavoritesEffect>() {
    override fun setInitialState(): FavoritesState {
        return FavoritesState()
    }

    override fun handleEvents(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.OnBackClicked -> {
                setEffect { FavoritesEffect.NavigateBack }
            }

            is FavoritesEvent.OnFavoriteClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val favorite = Favorite(
                        id = event.product.id,
                        image = event.product.image,
                        name = event.product.name,
                        price = event.product.price,
                    )
                    shopDAO.toggleFavorite(favorite)
                }
            }

            is FavoritesEvent.OnNavigateToDetail -> {
                setEffect { FavoritesEffect.NavigateToDetail(event.productId) }
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            shopDAO.getAllFavorites().collect { favorites ->
                setState { copy(favorites = favorites.toImmutableList()) }
            }
        }
    }
}

sealed interface FavoritesEvent : Event {
    data object OnBackClicked : FavoritesEvent
    data class OnNavigateToDetail(val productId: String) : FavoritesEvent
    data class OnFavoriteClicked(val product: Favorite) : FavoritesEvent
}

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: ImmutableList<Favorite> = persistentListOf()
) : State {
    val showEmptyText: Boolean
        get() = favorites.isEmpty()
}

sealed interface FavoritesEffect : Effect {
    data object NavigateBack : FavoritesEffect
    data class NavigateToDetail(val productId: String) : FavoritesEffect
}