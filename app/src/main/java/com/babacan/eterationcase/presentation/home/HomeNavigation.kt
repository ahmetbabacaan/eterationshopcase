package com.babacan.eterationcase.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HomeRoute = "HomeRoute"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HomeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
) {
    composable(
        route = HomeRoute,
    ) {
        HomeRoute(
            navigateBack = navigateBack,
            navigateToDetail = { productId ->
                navigateToDetail(productId)
            }
        )
    }
}
