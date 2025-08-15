package com.babacan.eterationcase.presentation.profile

import com.babacan.eterationcase.presentation.base.BaseViewModel
import com.babacan.eterationcase.presentation.base.Effect
import com.babacan.eterationcase.presentation.base.Event
import com.babacan.eterationcase.presentation.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :
    BaseViewModel<ProfileEvent, ProfileState, ProfileEffect>() {
    override fun setInitialState(): ProfileState {
        return ProfileState()
    }

    override fun handleEvents(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnBackClicked -> {
                setEffect { ProfileEffect.NavigateBack }
            }
        }
    }
}

sealed interface ProfileEvent : Event {
    data object OnBackClicked : ProfileEvent
}

data class ProfileState(
    val isLoading: Boolean = false
) : State

sealed interface ProfileEffect : Effect {
    data object NavigateBack : ProfileEffect
}