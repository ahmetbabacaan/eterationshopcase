package com.babacan.eterationcase

import app.cash.turbine.test
import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.repository.ShopProductRepository
import com.babacan.eterationcase.domain.use_cases.GetProductsUseCase
import com.babacan.eterationcase.domain.use_cases.GetProductsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest {

    private lateinit var repository: ShopProductRepository
    private lateinit var shopDAO: ShopDAO
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        repository = mock()
        shopDAO = mock()
        getProductsUseCase = GetProductsUseCaseImpl(repository, shopDAO)
    }

    @Test
    fun `should emit Loading state`() = runTest {
        whenever(repository.getProducts()).thenReturn(flowOf(Result.Loading))

        getProductsUseCase().test {
            assert(awaitItem() is Result.Loading)
            awaitComplete()
        }
    }

    @Test
    fun `should emit Error state`() = runTest {
        whenever(repository.getProducts()).thenReturn(flowOf(Result.Error("Network error")))

        getProductsUseCase().test {
            val item = awaitItem()
            assert(item is Result.Error && item.message == "Network error")
            awaitComplete()
        }
    }

    @Test
    fun `should emit Success state with favorites marked`() = runTest {
        val productList = listOf(
            ShopProduct(
                id = "1",
                name = "Product 1",
                price = "100.0",
                image = "https://example.com/product1.png",
                description = "Description for Product 1",
                brand = "BrandA",
                model = "ModelX",
                createdAt = "2023-07-17T05:37:40.184Z",
                quantity = 10,
                isFavorite = false
            ),
            ShopProduct(
                id = "2",
                name = "Product 2",
                price = "200.0",
                image = "https://example.com/product2.png",
                description = "Description for Product 2",
                brand = "BrandB",
                model = "ModelY",
                createdAt = "2023-07-17T05:37:40.184Z",
                quantity = 5,
                isFavorite = false
            )
        )

        val favorites = listOf(
            Favorite(
                id = "2",
                image = "https://example.com/product2.png",
                name = "Product 2",
                price = "200.0"
            )
        )

        whenever(repository.getProducts()).thenReturn(
            flowOf(Result.Success(productList))
        )
        whenever(shopDAO.getAllFavorites()).thenReturn(
            flowOf(favorites)
        )

        getProductsUseCase().test {
            val item = awaitItem()
            assert(item is Result.Success)

            val data = (item as Result.Success).data
            assert(data.first { it.id == "2" }.isFavorite)
            assert(!data.first { it.id == "1" }.isFavorite)
            awaitComplete()
        }
    }
}
