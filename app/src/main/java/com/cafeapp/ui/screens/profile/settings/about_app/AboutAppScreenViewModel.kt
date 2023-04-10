package com.cafeapp.ui.screens.profile.settings.about_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.ui.screens.profile.settings.about_app.states.AboutAppScreenEffect
import com.cafeapp.ui.screens.profile.settings.about_app.states.AboutAppScreenEvent
import com.cafeapp.ui.screens.profile.settings.about_app.states.AboutAppScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutAppScreenViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow<AboutAppScreenState>(AboutAppScreenState.Data)
    val screenState = _screenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<AboutAppScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

    fun onEvent(event: AboutAppScreenEvent) {
        when (event) {
            AboutAppScreenEvent.OnBackButtonClicked -> onBackButtonClicked()
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _screenEffect.emit(AboutAppScreenEffect.NavigateBack)
        }
    }
}