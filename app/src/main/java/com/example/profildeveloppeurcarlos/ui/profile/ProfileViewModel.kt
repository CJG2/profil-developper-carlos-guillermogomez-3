package com.example.profildeveloppeurcarlos.ui.profile

import androidx.lifecycle.ViewModel
import com.example.profildeveloppeurcarlos.data.carlosProfile
import com.example.profildeveloppeurcarlos.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Gère l'état principal de l'écran de profil : recherche, filtre favoris, favoris et liste affichée.
// Il ne connaît aucun composant Compose : il reçoit des actions et publie un nouvel état.
// Le profil a une valeur par défaut : viewModel() peut créer le ViewModel sans paramètre.
class ProfileViewModel(
    profile: Profile = carlosProfile
) : ViewModel() {

    // État modifiable : privé, seul le ViewModel peut le changer.
    private val _uiState = MutableStateFlow(ProfileUiState(profile = profile))

    // Version publique en lecture seule, observée par l'interface.
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        updateState { currentState ->
            currentState.copy(searchQuery = query)
        }
    }

    fun onToggleFavoriteFilter() {
        updateState { currentState ->
            currentState.copy(showOnlyFavorites = !currentState.showOnlyFavorites)
        }
    }

    // Ajoute le projet aux favoris s'il n'y est pas, sinon le retire.
    fun onFavoriteClick(projectId: Int) {
        updateState { currentState ->
            val favoriteIds = currentState.favoriteProjectIds
            val newFavoriteIds = if (projectId in favoriteIds) {
                favoriteIds - projectId
            } else {
                favoriteIds + projectId
            }
            currentState.copy(favoriteProjectIds = newFavoriteIds)
        }
    }

    // Toutes les actions passent par ici : on applique le changement, puis on recalcule
    // la liste affichée. Elle reste donc toujours cohérente avec la recherche, le filtre
    // et les favoris (par exemple, retirer un favori avec le filtre actif le fait disparaître).
    private fun updateState(change: (ProfileUiState) -> ProfileUiState) {
        _uiState.update { currentState ->
            val newState = change(currentState)
            newState.copy(
                visibleProjects = filterProjects(
                    projects = newState.profile.projects,
                    searchQuery = newState.searchQuery,
                    showOnlyFavorites = newState.showOnlyFavorites,
                    favoriteProjectIds = newState.favoriteProjectIds
                )
            )
        }
    }
}
