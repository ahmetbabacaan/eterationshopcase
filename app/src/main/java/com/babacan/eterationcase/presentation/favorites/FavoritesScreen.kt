package com.babacan.eterationcase.presentation.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babacan.eterationcase.presentation.home.ProductCard
import com.babacan.eterationcase.ui.theme.Montserrat

@Composable
fun FavoritesRoute(
    navigateBack: () -> Unit,
    navigateToDetail: (id: String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                FavoritesEffect.NavigateBack -> navigateBack()
                is FavoritesEffect.NavigateToDetail -> {
                    navigateToDetail(effect.productId)
                }
            }
        }
    }

    FavoritesScreen(
        viewState = viewState,
        onViewEvent = viewModel::setEvent
    )

}

@Composable
fun FavoritesScreen(
    viewState: FavoritesState,
    onViewEvent: (FavoritesEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .systemBarsPadding(), topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF2A59FE)),
            ) {

                Text(
                    text = "E-Favorites",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 6.dp, bottom = 14.dp),
                    fontSize = 24.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = Color.White), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewState.showEmptyText) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your favorites list is empty",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp),
                    contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                    columns = GridCells.Fixed(2),
                ) {
                    items(
                        count = viewState.favorites.size,
                        key = { viewState.favorites[it].id }
                    ) {
                        ProductCard(
                            imageUrl = viewState.favorites[it].image,
                            price = viewState.favorites[it].price,
                            title = viewState.favorites[it].name,
                            isFavorite = true,
                            showAddToCart = false,
                            onNavigateToDetail = {
                                onViewEvent(
                                    FavoritesEvent.OnNavigateToDetail(
                                        viewState.favorites[it].id
                                    )
                                )
                            },
                            onAddToCartClick = { },
                            onFavoriteClick = {
                                onViewEvent(
                                    FavoritesEvent.OnFavoriteClicked(
                                        viewState.favorites[it]
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoritesScreenPreview() {
    FavoritesScreen(
        viewState = FavoritesState(),
        onViewEvent = { }
    )
}
