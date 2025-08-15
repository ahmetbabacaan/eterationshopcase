package com.babacan.eterationcase.viewmodel

import app.cash.turbine.test
import com.babacan.eterationcase.presentation.profile.ProfileEffect
import com.babacan.eterationcase.presentation.profile.ProfileEvent
import com.babacan.eterationcase.presentation.profile.ProfileState
import com.babacan.eterationcase.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is default`() = runTest {
        val vm = ProfileViewModel()
        assertEquals(ProfileState(), vm.state.value)
    }

    @Test
    fun `OnBackClicked emits NavigateBack effect`() = runTest {
        val vm = ProfileViewModel()

        vm.effect.test {
            vm.setEvent(ProfileEvent.OnBackClicked)

            dispatcher.scheduler.advanceUntilIdle()

            assertEquals(ProfileEffect.NavigateBack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}