package com.babacan.eterationcase.presentation.basket

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val BasketRoute = "BasketRoute"

fun NavController.navigateToBasket(navOptions: NavOptions? = null) {
    this.navigate(BasketRoute, navOptions)
}

fun NavGraphBuilder.basketScreen(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(
        route = BasketRoute,
    ) {
        BasketRoute(
            navigateBack = navigateBack,
            navigateToHome = navigateToHome,
        )
    }
}
