package com.babacan.eterationcase.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.babacan.eterationcase.ui.ShopNavDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ShopBottomNavBar(
    destinations: ImmutableList<ShopNavDestination>,
    currentDestination: NavDestination?,
    onItemSelected: (ShopNavDestination, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BottomNavBarViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    val navItemColors = NavigationBarItemDefaults.colors(
        indicatorColor = Color.White,
        selectedIconColor = Color.Blue,
        unselectedIconColor = Color.Black,
    )
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            tonalElevation = 0.dp,
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                if (destination == ShopNavDestination.Basket) {

                    NavigationBarItem(
                        modifier = Modifier.requiredWidth(80.dp),
                        selected = selected,
                        colors = navItemColors,
                        onClick = { onItemSelected(destination, selected) },
                        icon = {
                            Box {
                                Column(
                                    modifier = Modifier
                                        .padding(start = 24.dp)
                                        .size(24.dp)
                                        .background(
                                            color = Color(0xFFF90000),
                                            shape = RoundedCornerShape(50.dp),
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = viewState.basketProductsSize.toString(),
                                            fontSize = 16.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = null,
                                )
                            }

                        },
                    )


                } else {
                    NavigationBarItem(
                        modifier = Modifier.requiredWidth(80.dp),
                        selected = selected,
                        colors = navItemColors,
                        onClick = { onItemSelected(destination, selected) },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = null,
                            )
                        },
                    )

                }
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: ShopNavDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false

@Preview
@Composable
private fun ShopBottomBarPreview() {

    val topLevelDestinations: ImmutableList<ShopNavDestination> = persistentListOf(
        ShopNavDestination.Home,
        ShopNavDestination.Basket,
        ShopNavDestination.Favorites,
        ShopNavDestination.Profile,
    )

    ShopBottomNavBar(
        destinations = topLevelDestinations,
        currentDestination = null,
        onItemSelected = { _, _ -> },
        modifier = Modifier.fillMaxWidth()
    )
}