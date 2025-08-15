package com.babacan.eterationcase.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.babacan.eterationcase.presentation.components.LoadingContent
import com.babacan.eterationcase.ui.icons.IconStarSelected
import com.babacan.eterationcase.ui.icons.IconStarUnselected
import com.babacan.eterationcase.ui.theme.Montserrat

@Composable
fun DetailRoute(
    navigateBack: () -> Unit,
    productId: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.setEvent(DetailEvent.SetProductId(productId))
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DetailEffect.NavigateBack -> navigateBack()
            }
        }
    }

    DetailScreen(
        viewState = viewState,
        onViewEvent = viewModel::setEvent
    )

}


@Composable
fun DetailScreen(
    viewState: DetailState,
    onViewEvent: (DetailEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .systemBarsPadding(), topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF2A59FE)),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                IconButton(
                    onClick = { onViewEvent(DetailEvent.OnBackClicked) },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Text(
                    text = viewState.product?.name.orEmpty(),
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .align (Alignment.CenterVertically)
                        .padding(end = 75.dp, start = 35.dp),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoadingContent(viewState.isLoading) {
                viewState.product?.let { product ->
                    ProductDetailCard(
                        imageUrl = product.image,
                        title = product.name,
                        description = product.description,
                        priceText = "${product.price} ₺",
                        isFavorite = product.isFavorite,
                        onToggleFavorite = { onViewEvent(DetailEvent.OnToggleFavorite) },
                        onAddToCart = { onViewEvent(DetailEvent.OnAddToCart) },
                    )
                } ?: run {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Product not found",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun ProductDetailCard(
    imageUrl: String,
    title: String,
    description: String,
    priceText: String,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val blue = Color(0xFF2962FF)
    val titleWeight = FontWeight.ExtraBold
    val bodyColor = Color(0xFF374151)

    Column(modifier = modifier.padding(12.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFE5E7EB))
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(28.dp)
            ) {
                Image(
                    imageVector = if (isFavorite) Icons.IconStarSelected else Icons.IconStarUnselected,
                    contentDescription = "Favorite",
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = title,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = titleWeight,
            fontFamily = Montserrat,
            color = Color(0xFF0F172A),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = description,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = bodyColor,
            fontFamily = Montserrat
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Price:",
                    color = blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Montserrat
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = priceText,
                    color = Color(0xFF0B0F19),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Montserrat
                )
            }

            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .height(48.dp)
                    .widthIn(min = 180.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text = "Add to Cart",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Montserrat
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        viewState = DetailState(),
        onViewEvent = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailCardPreview() {
    ProductDetailCard(
        imageUrl = "",
        title = "Apple iPhone 14 Pro Max 256 GB",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sodales nibh pretium ipsum faucibus...",
        priceText = "124124124 ₺",
        isFavorite = true,
        onToggleFavorite = {},
        onAddToCart = {},
    )
}
