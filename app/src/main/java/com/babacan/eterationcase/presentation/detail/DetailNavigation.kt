package com.babacan.eterationcase.presentation.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val DetailRoute = "DetailRoute"
const val DetailRouteWithArgs = "$DetailRoute?productId={productId}"


fun NavController.navigateToProductDetail(navOptions: NavOptions? = null, productId: String) {
    this.navigate("$DetailRoute?productId=$productId", navOptions)
}

fun NavGraphBuilder.detailScreen(
    navigateBack: () -> Unit,
) {
    composable(
        route = DetailRouteWithArgs,
        arguments = listOf(
            navArgument("productId") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId").orEmpty()
        DetailRoute(
            navigateBack = navigateBack,
            productId = productId
        )
    }
}
