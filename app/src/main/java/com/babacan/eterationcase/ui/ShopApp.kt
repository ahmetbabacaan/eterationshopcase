package com.babacan.eterationcase.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.babacan.eterationcase.navigation.MainNavHost
import com.babacan.eterationcase.navigation.ShopMainAppState
import com.babacan.eterationcase.navigation.rememberShopMainAppState

@Composable
fun ShopApp(
    appState: ShopMainAppState = rememberShopMainAppState(),
) {
    MainNavHost(
        navController = appState.navController,
        startDestination = appState.startDestination,
        modifier = Modifier,
        shopMainAppState = appState,
    )
}