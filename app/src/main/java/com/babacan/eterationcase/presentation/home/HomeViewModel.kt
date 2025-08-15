package com.babacan.eterationcase.presentation.home

import androidx.lifecycle.viewModelScope
import com.babacan.eterationcase.common.isoDateToTimeStamp
import com.babacan.eterationcase.common.priceToDouble
import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.use_cases.GetProductsUseCaseImpl
import com.babacan.eterationcase.presentation.base.BaseViewModel
import com.babacan.eterationcase.presentation.base.Effect
import com.babacan.eterationcase.presentation.base.Event
import com.babacan.eterationcase.presentation.base.State
import com.babacan.eterationcase.presentation.home.HomeEffect.NavigateToDetail
import com.babacan.eterationcase.presentation.home.filters.FilterModel
import com.babacan.eterationcase.presentation.home.filters.SortPriorities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCaseImpl: GetProductsUseCaseImpl,
    private val shopDAO: ShopDAO
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    override fun setInitialState(): HomeState {
        return HomeState()
    }

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            HomeEvent.OnBackClicked -> {
                setEffect { HomeEffect.NavigateBack }
            }

            is HomeEvent.OnAddToCartClicked -> {
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

            is HomeEvent.OnNavigateToDetail -> {
                setEffect { NavigateToDetail(event.productId) }
            }

            is HomeEvent.OnSearchTextChanged -> {
                if (event.query.isEmpty()) {
                    setState { copy(filteredProducts = products) }
                    return
                }
                val query = event.query.lowercase()
                setState {
                    copy(filteredProducts = products.filter {
                        it.name.lowercase().contains(query)
                    }.toImmutableList())
                }
            }

            is HomeEvent.OnFavoriteClicked -> {
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

            is HomeEvent.OnSelectFilterClicked -> {
                setState { copy(showFilter = true) }
            }

            HomeEvent.OnDismissFilter -> {
                setState { copy(showFilter = false) }
            }

            is HomeEvent.OnFilterApplied -> {
                setState { copy(filteredProducts = getCurrentState().products) }
                sortPriorities(event.priorities)
                sortBrands(event.brands)
                sortModels(event.models)
                setState {
                    copy(
                        showFilter = false,
                        filterModel = FilterModel(
                            selectedBrands = event.brands,
                            selectedModels = event.models,
                            sort = event.priorities,
                        ),
                    )
                }
                setState { copy(resetListPosition = true) }
            }

            HomeEvent.ClearResetListPosition -> {
                setState { copy(resetListPosition = false) }
            }
        }
    }

    private fun sortModels(strings: Set<String>) {
        if (strings.isEmpty()) {
            return
        }
        val filteredProducts = getCurrentState().filteredProducts.filter { product ->
            strings.contains(product.model)
        }.toImmutableList()
        setState { copy(filteredProducts = filteredProducts) }
    }

    private fun sortBrands(strings: Set<String>) {
        if (strings.isEmpty()) {
            return
        }
        val filteredProducts = getCurrentState().filteredProducts.filter { product ->
            strings.contains(product.brand)
        }.toImmutableList()
        setState { copy(filteredProducts = filteredProducts) }
    }

    private fun sortPriorities(priorities: SortPriorities) {
        when(priorities) {
            SortPriorities.OLD_TO_NEW -> {
                setState {
                    copy(
                        filteredProducts = filteredProducts.sortedBy { it.createdAt.isoDateToTimeStamp() }.toImmutableList()
                    )
                }
            }

            SortPriorities.NEW_TO_OLD -> {
                setState {
                    copy(
                        filteredProducts = filteredProducts.sortedByDescending { it.createdAt.isoDateToTimeStamp() }.toImmutableList()
                    )
                }
            }

            SortPriorities.HIGH_TO_LOW -> {
                setState {
                    copy(
                        filteredProducts = filteredProducts.sortedByDescending { it.price.priceToDouble() }.toImmutableList()
                    )
                }
            }

            SortPriorities.LOW_TO_HIGH -> {
                setState {
                    copy(
                        filteredProducts = filteredProducts.sortedBy { it.price.priceToDouble() }.toImmutableList()
                    )
                }
            }
        }
    }

    init {
        getProducts()
    }

    private fun getProducts() {
        setState { copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            getProductsUseCaseImpl().collect { state ->
                setState { copy(isLoading = state == Result.Loading) }
                when (state) {
                    Result.Loading -> {}
                    is Result.Error -> {
                        //do nothing for now, maybe show a snackbar
                    }

                    is Result.Success<List<ShopProduct>> -> {
                        setState { copy(products = state.data.toImmutableList()) }
                        if (getCurrentState().filteredProducts.isEmpty()) {
                            setState { copy(filteredProducts = state.data.toImmutableList()) }
                        } else {
                            val filteredProducts = state.data.filter { product ->
                                getCurrentState().filteredProducts.any { it.id == product.id }
                            }.toImmutableList()
                            setState { copy(filteredProducts = filteredProducts) }
                        }
                    }
                }
            }
        }
    }
}

sealed interface HomeEvent : Event {
    data object OnBackClicked : HomeEvent
    data object OnSelectFilterClicked : HomeEvent
    data object OnDismissFilter : HomeEvent
    data object ClearResetListPosition : HomeEvent

    data class OnSearchTextChanged(val query: String) : HomeEvent
    data class OnNavigateToDetail(val productId: String) : HomeEvent
    data class OnFavoriteClicked(val product: ShopProduct) : HomeEvent
    data class OnAddToCartClicked(val product: ShopProduct) : HomeEvent
    data class OnFilterApplied(
        val priorities: SortPriorities,
        val models: Set<String>,
        val brands: Set<String>
    ) : HomeEvent

}

data class HomeState(
    val isLoading: Boolean = false,
    val products: ImmutableList<ShopProduct> = persistentListOf(),
    val showFilter: Boolean = false,
    val resetListPosition: Boolean = false,
    val filterModel: FilterModel = FilterModel(),
    val filteredProducts: ImmutableList<ShopProduct> = persistentListOf(),
) : State {
    val filteredBrands: Set<String>
        get() = filteredProducts.map { it.brand }.toSet()

    val filteredModels: Set<String>
        get() = filteredProducts.map { it.model }.toSet()

    val showModel: Boolean
        get() = filterModel.selectedModels.isEmpty()
}

sealed interface HomeEffect : Effect {
    data object NavigateBack : HomeEffect
    data class NavigateToDetail(val productId: String) : HomeEffect
}