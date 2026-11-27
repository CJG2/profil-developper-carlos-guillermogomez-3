package com.example.profildeveloppeurcarlos.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

// Container : fait le lien entre le ViewModel et Compose.
//   ViewModel --publie l'état--> Container --transmet les valeurs--> ProfileScreen
//   ProfileScreen --événement (callback)--> ViewModel
@Composable
fun ProfileContainer(
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    modifier: Modifier = Modifier,
    // 1. Récupère le ViewModel (le même est conservé tant que l'écran existe, même après une rotation).
    viewModel: ProfileViewModel = viewModel()
) {
    // 2. Collecte le StateFlow : à chaque nouvel état publié, Compose recompose l'écran.
    val uiState by viewModel.uiState.collectAsState()

    // 3. Transmet les valeurs à l'écran et 4. les fonctions du ViewModel comme callbacks.
    ProfileScreen(
        profile = uiState.profile,
        searchQuery = uiState.searchQuery,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        showOnlyFavorites = uiState.showOnlyFavorites,
        onToggleFavoriteFilter = viewModel::onToggleFavoriteFilter,
        favoriteProjectIds = uiState.favoriteProjectIds,
        onFavoriteClick = viewModel::onFavoriteClick,
        visibleProjects = uiState.visibleProjects,
        onContactClick = onContactClick,
        onPortfolioClick = onPortfolioClick,
        modifier = modifier
    )
}
