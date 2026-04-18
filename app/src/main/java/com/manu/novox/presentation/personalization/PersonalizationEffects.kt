package com.manu.novox.presentation.personalization

sealed interface PersonalizationEffects {
    data class ShowToast(val message: String): PersonalizationEffects

    object NavigateToSettings: PersonalizationEffects
}