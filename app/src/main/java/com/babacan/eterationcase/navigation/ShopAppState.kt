package com.babacan.eterationcase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.babacan.eterationcase.presentation.basket.BasketRoute
import com.babacan.eterationcase.presentation.favorites.FavoritesRoute
import com.babacan.eterationcase.presentation.home.HomeRoute
import com.babacan.eterationcase.presentation.profile.ProfileRoute
import com.babacan.eterationcase.ui.ShopNavDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberShopMainAppState(
    navController: NavHostController = rememberNavController(),
): ShopMainAppState {
    return remember(navController) {
        ShopMainAppState(navController)
    }
}

@Stable
class ShopMainAppState(
    val navController: NavHostController,
) {

    val startDestination: String
        get() = HomeRoute

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.hierarchy?.any {
            it.route?.contains(HomeRoute) ?: false
                    || it.route?.contains(BasketRoute) ?: false
                    || it.route?.contains(FavoritesRoute) ?: false
                    || it.route?.contains(ProfileRoute) ?: false
        } ?: false

    @Stable
    val topLevelDestinations: ImmutableList<ShopNavDestination> = persistentListOf(
        ShopNavDestination.Home,
        ShopNavDestination.Basket,
        ShopNavDestination.Favorites,
        ShopNavDestination.Profile,
    )

    fun navigateToTopLevelDestination(
        topLevelDestination: ShopNavDestination,
        reSelectedItem: Boolean = false,
    ) {
        val topLevelNavOptions = navOptions {
            popUpTo(HomeRoute) {
                saveState = true
            }
            launchSingleTop = reSelectedItem.not()
        }

        when (topLevelDestination) {
            ShopNavDestination.Home -> {
                navController.navigate(HomeRoute, topLevelNavOptions)
            }

            ShopNavDestination.Basket -> {
                navController.navigate(ShopNavDestination.Basket.route, topLevelNavOptions)
            }

            ShopNavDestination.Favorites -> {
                navController.navigate(ShopNavDestination.Favorites.route, topLevelNavOptions)
            }

            ShopNavDestination.Profile -> {
                navController.navigate(ShopNavDestination.Profile.route, topLevelNavOptions)
            }
        }
    }
}