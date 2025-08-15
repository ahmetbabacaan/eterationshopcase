package com.babacan.eterationcase.presentation.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ProfileRoute = "ProfileRoute"


fun NavGraphBuilder.profileScreen(
    navigateBack: () -> Unit,
) {
    composable(
        route = ProfileRoute,
    ) {
        ProfileRoute(
            navigateBack = navigateBack,
        )
    }
}
