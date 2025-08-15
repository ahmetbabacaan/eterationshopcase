package com.babacan.eterationcase.viewmodel

import app.cash.turbine.test
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.presentation.favorites.FavoritesEffect
import com.babacan.eterationcase.presentation.favorites.FavoritesEvent
import com.babacan.eterationcase.presentation.favorites.FavoritesViewModel
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var shopDAO: ShopDAO

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        shopDAO = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnBackClicked emits NavigateBack effect`() = runTest {
        every { shopDAO.getAllFavorites() } returns flowOf(emptyList())
        val vm = FavoritesViewModel(shopDAO)

        vm.effect.test {
            vm.setEvent(FavoritesEvent.OnBackClicked)
            advanceUntilIdle()

            assertEquals(FavoritesEffect.NavigateBack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnNavigateToDetail emits NavigateToDetail(productId)`() = runTest {
        every { shopDAO.getAllFavorites() } returns flowOf(emptyList())
        val vm = FavoritesViewModel(shopDAO)

        vm.effect.test {
            vm.setEvent(FavoritesEvent.OnNavigateToDetail("p42"))
            advanceUntilIdle()

            assertEquals(FavoritesEffect.NavigateToDetail("p42"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}