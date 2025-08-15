package com.babacan.eterationcase.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.babacan.eterationcase.presentation.basket.basketScreen
import com.babacan.eterationcase.presentation.detail.detailScreen
import com.babacan.eterationcase.presentation.detail.navigateToProductDetail
import com.babacan.eterationcase.presentation.favorites.favoritesScreen
import com.babacan.eterationcase.presentation.home.HomeRoute
import com.babacan.eterationcase.presentation.home.homeScreen
import com.babacan.eterationcase.presentation.home.navigateToHome
import com.babacan.eterationcase.presentation.profile.profileScreen

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeRoute,
    shopMainAppState: ShopMainAppState,
) {

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
        ),
        bottomBar = {
            AnimatedVisibility(
                visible = shopMainAppState.shouldShowBottomBar,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 200 },
                    animationSpec = tween(200),
                ),
                exit = fadeOut() + slideOutVertically(
                    targetOffsetY = { 200 },
                    animationSpec = tween(200),
                ),
            ) {
                CompositionLocalProvider(
                    value = LocalRippleConfiguration provides RippleConfiguration(
                        rippleAlpha = RippleAlpha(
                            pressedAlpha = 0.0f,
                            focusedAlpha = 0.0f,
                            draggedAlpha = 0.0f,
                            hoveredAlpha = 0.0f
                        ),
                        color = Color.Black

                    )
                ) {
                    ShopBottomNavBar(
                        destinations = shopMainAppState.topLevelDestinations,
                        currentDestination = shopMainAppState.currentDestination,
                        onItemSelected = { navDestinations, selected ->
                            if (selected) {
                                shopMainAppState.navController.currentBackStack.value
                                    .map { it.destination.route }
                                    .forEach { route ->
                                        if (route?.contains(navDestinations.route) == true && shopMainAppState.navController.currentDestination?.route != navDestinations.route) {
                                            shopMainAppState.navController.popBackStack(
                                                route = navDestinations.route,
                                                inclusive = false,
                                                saveState = false,
                                            )
                                        }
                                    }
                            }
                            shopMainAppState.navigateToTopLevelDestination(
                                topLevelDestination = navDestinations,
                                reSelectedItem = selected,
                            )
                        },
                    )
                }
            }
        },
        topBar = {
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(200)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) },
            popEnterTransition = { fadeIn(animationSpec = tween(200)) },
            popExitTransition = { fadeOut(animationSpec = tween(200)) },
        ) {
            homeScreen(
                navigateBack = { navController.popBackStack() },
                navigateToDetail = { id ->
                    navController.navigateToProductDetail(productId = id)
                }
            )

            basketScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = { navController.navigateToHome() }
            )

            favoritesScreen(
                navigateBack = { navController.popBackStack() },
                navigateToDetail = { id ->
                    navController.navigateToProductDetail(productId = id)
                }
            )

            profileScreen(
                navigateBack = { navController.popBackStack() },
            )

            detailScreen(
                navigateBack = { navController.popBackStack() },
            )

        }
    }

}