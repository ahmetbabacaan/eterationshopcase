package com.babacan.eterationcase.ui

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.babacan.eterationcase.presentation.basket.BasketRoute
import com.babacan.eterationcase.presentation.favorites.FavoritesRoute
import com.babacan.eterationcase.presentation.home.HomeRoute
import com.babacan.eterationcase.presentation.profile.ProfileRoute
import com.babacan.eterationcase.ui.icons.IconBasketOutline
import com.babacan.eterationcase.ui.icons.IconHomeOutline
import com.babacan.eterationcase.ui.icons.IconPersonOutline
import com.babacan.eterationcase.ui.icons.IconStarOutline

sealed interface ShopNavDestination {
    val route: String
    val icon: ImageVector

    data object Home : ShopNavDestination {
        override val route = HomeRoute
        override val icon = Icons.IconHomeOutline
    }

    data object Basket : ShopNavDestination {
        override val route = BasketRoute
        override val icon = Icons.IconBasketOutline
    }

    data object Favorites : ShopNavDestination {
        override val route = FavoritesRoute
        override val icon = Icons.IconStarOutline
    }

    data object Profile : ShopNavDestination {
        override val route = ProfileRoute
        override val icon = Icons.IconPersonOutline
    }
}
