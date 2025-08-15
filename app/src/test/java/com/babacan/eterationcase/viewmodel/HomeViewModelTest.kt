package com.babacan.eterationcase.viewmodel

import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.use_cases.GetProductsUseCaseImpl
import com.babacan.eterationcase.presentation.home.HomeEffect
import com.babacan.eterationcase.presentation.home.HomeEvent
import com.babacan.eterationcase.presentation.home.HomeViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {


    private val dispatcher = StandardTestDispatcher()

    private val mockUseCase: GetProductsUseCaseImpl = mockk()
    private val mockShopDAO: ShopDAO = mockk()

    @Before
    fun setup() {
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `OnSelectFilterClicked sets showFilter true`() = runTest {
        val vm = HomeViewModel(mockUseCase, mockShopDAO)

        vm.setEvent(HomeEvent.OnSelectFilterClicked)
        advanceUntilIdle()

        assertEquals(true, vm.state.value.showFilter)
    }


}