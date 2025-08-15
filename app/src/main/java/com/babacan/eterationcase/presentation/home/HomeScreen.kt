package com.babacan.eterationcase.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.presentation.components.LoadingContent
import com.babacan.eterationcase.presentation.home.filters.ShowFilterBottomSheet
import com.babacan.eterationcase.ui.icons.IconStarSelected
import com.babacan.eterationcase.ui.icons.IconStarUnselected
import com.babacan.eterationcase.ui.icons.Searchicon
import com.babacan.eterationcase.ui.theme.Montserrat
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeEffect.NavigateBack -> navigateBack()
                is HomeEffect.NavigateToDetail -> {
                    navigateToDetail(effect.productId)
                }
            }
        }
    }

    HomeScreen(
        viewState = viewState, onViewEvent = viewModel::setEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewState: HomeState, onViewEvent: (HomeEvent) -> Unit
) {
    val listState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

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
                    text = "E-Market",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 6.dp, bottom = 14.dp),
                    fontSize = 24.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )


            }
        }) { contentPadding ->

        if (viewState.showFilter) {
            ShowFilterBottomSheet(
                viewState = viewState,
                onApply = { sort, selectedBrands, selectedModels ->
                    onViewEvent(HomeEvent.OnDismissFilter)
                    onViewEvent(
                        HomeEvent.OnFilterApplied(
                            sort,
                            selectedModels,
                            selectedBrands
                        )
                    )
                },
                onDismiss = { onViewEvent(HomeEvent.OnDismissFilter) }
            )
        }

        if (viewState.resetListPosition) {
            LaunchedEffect(Unit) {
                scope.launch {
                    listState.scrollToItem(0)
                }
                onViewEvent(HomeEvent.ClearResetListPosition)
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = Color.White), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                onSearchQuery = { onViewEvent(HomeEvent.OnSearchTextChanged(it)) }
            )

            FiltersRow(
                onSelectFilterClick = { onViewEvent(HomeEvent.OnSelectFilterClicked) }
            )


            LoadingContent(viewState.isLoading) {
                LazyVerticalGrid(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp),
                    contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                    columns = GridCells.Fixed(2),
                ) {
                    items(
                        count = viewState.filteredProducts.size,
                        key = { viewState.filteredProducts[it].id }
                    ) {
                        ProductCard(
                            imageUrl = viewState.filteredProducts[it].image,
                            price = viewState.filteredProducts[it].price,
                            title = viewState.filteredProducts[it].name,
                            isFavorite = viewState.filteredProducts[it].isFavorite,
                            onNavigateToDetail = {
                                onViewEvent(
                                    HomeEvent.OnNavigateToDetail(
                                        viewState.filteredProducts[it].id
                                    )
                                )
                            },
                            onAddToCartClick = { onViewEvent(HomeEvent.OnAddToCartClicked(viewState.filteredProducts[it])) },
                            onFavoriteClick = { onViewEvent(HomeEvent.OnFavoriteClicked(viewState.filteredProducts[it])) }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchQuery: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isFocused) Color(0xFFF5F5F5) else Color(0xFFF8F9FA)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Searchicon,
            contentDescription = "",
            tint = Color(0xFF6B7280),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchQuery(it)
            },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color(0xFF1F2937)
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearchQuery(searchText)
                }
            ),
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) {
                    Text(
                        text = "Search",
                        color = Color(0xFF9CA3AF),
                        fontSize = 18.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                }
                innerTextField()
            },
            singleLine = true
        )
    }
}

@Composable
fun FiltersRow(
    onSelectFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Filters:",
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 18.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium
        )

        Box(
            modifier = Modifier
                .width(158.dp)
                .height(36.dp)
                .background(Color(0xFFD9D9D9), shape = RoundedCornerShape(4.dp))
                .clickable { onSelectFilterClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "Select Filter",
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun ProductCard(
    imageUrl: String,
    price: String,
    title: String,
    isFavorite: Boolean,
    showAddToCart: Boolean = true,
    onAddToCartClick: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onFavoriteClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            .clickable { onNavigateToDetail() },
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color(0xFFE5E7EB))
                )

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Image(
                        imageVector = if (isFavorite) Icons.IconStarSelected else Icons.IconStarUnselected,
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onFavoriteClick() })
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = price,
                color = Color(0xFF2962FF),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Montserrat
            )

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = Montserrat
            )

            Spacer(Modifier.height(12.dp))

            if (showAddToCart) {
                Button(
                    onClick = onAddToCartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2A59FE),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = Montserrat
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProductCard() {
    ProductCard(
        imageUrl = "",
        price = "15.000 ₺",
        title = "iPhone 13 Pro Max 256Gb",
        isFavorite = true,
        onAddToCartClick = {},
        onNavigateToDetail = {})
}

val products = List(20) {
    ShopProduct(
        id = it.toString(),
        name = "Product $it",
        price = "1234.56",
        image = "https://via.placeholder.com/150",
        description = "harika ürün",
        brand = "Apple",
        model = "13 Pro",
        createdAt = "2023-07-17T02:49:46.692Z"
    )
}.toImmutableList()

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        viewState = HomeState(filteredProducts = products), onViewEvent = { })
}
