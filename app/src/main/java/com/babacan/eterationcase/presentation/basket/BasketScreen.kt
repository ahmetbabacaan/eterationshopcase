package com.babacan.eterationcase.presentation.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.presentation.home.products
import com.babacan.eterationcase.ui.icons.IcMinus
import com.babacan.eterationcase.ui.icons.IcPlus
import com.babacan.eterationcase.ui.theme.Montserrat
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BasketRoute(
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: BasketViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                BasketEffect.NavigateBack -> navigateBack()
                BasketEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    BasketScreen(
        viewState = viewState,
        onViewEvent = viewModel::setEvent
    )

}

@Composable
fun BasketScreen(
    viewState: BasketState,
    onViewEvent: (BasketEvent) -> Unit
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
                    text = "E-Basket",
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
                .padding(bottom = 72.dp)
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewState.showEmptyBasketText) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your shopping cart is empty",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    items(viewState.products) { product ->
                        ProductItem(
                            product = product,
                            onIncreaseQuantity = { onViewEvent(BasketEvent.OnIncreaseQuantity(it)) },
                            onDecreaseQuantity = { onViewEvent(BasketEvent.OnDecreaseQuantity(it)) }
                        )
                    }
                }
            }

            TotalSection(
                viewState.totalPrice,
                onCompleteClick = { onViewEvent(BasketEvent.OnCompleteClick) }
            )
        }

    }
}

@Composable
fun TotalSection(
    totalPrice: String,
    onCompleteClick: () -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Total Section
        Column {
            Text(
                text = "Total:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                ),
                fontSize = 18.sp,
                color = Color.Blue
            )
            Text(
                text = totalPrice,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        // Complete Button
        Button(
            onClick = { onCompleteClick() },
            modifier = Modifier
                .width(120.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Complete",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun ProductItem(
    product: ShopProduct,
    onIncreaseQuantity: (ShopProduct) -> Unit = {},
    onDecreaseQuantity: (ShopProduct) -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Info
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = product.price,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
            )
        }

        Row(
            modifier = Modifier.padding(end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(
                onClick = { onDecreaseQuantity(product) },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.1f),
                    )
            ) {
                Icon(
                    imageVector = Icons.IcMinus,
                    contentDescription = "Azalt",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp),
                )
            }

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .background(Color.Blue, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.quantity.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            IconButton(
                onClick = { onIncreaseQuantity(product) },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.1f),
                    )
            ) {
                Icon(
                    imageVector = Icons.IcPlus,
                    contentDescription = "Artır",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(16.dp),
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BasketScreenPreview() {
    BasketScreen(
        viewState = BasketState(products = products),
        onViewEvent = { }
    )
}
