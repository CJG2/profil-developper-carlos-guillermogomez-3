package com.example.profildeveloppeurcarlos.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeurcarlos.data.carlosProfile
import com.example.profildeveloppeurcarlos.data.thomasProfile
import com.example.profildeveloppeurcarlos.model.Profile
import com.example.profildeveloppeurcarlos.model.Project
import com.example.profildeveloppeurcarlos.ui.components.ContactSection
import com.example.profildeveloppeurcarlos.ui.components.EmptyProjectsMessage
import com.example.profildeveloppeurcarlos.ui.components.ProfileActions
import com.example.profildeveloppeurcarlos.ui.components.ProfileHeader
import com.example.profildeveloppeurcarlos.ui.components.ProjectCard
import com.example.profildeveloppeurcarlos.ui.components.ProjectFilterBar
import com.example.profildeveloppeurcarlos.ui.components.ProjectSearchField
import com.example.profildeveloppeurcarlos.ui.components.SectionTitle
import com.example.profildeveloppeurcarlos.ui.components.TechnologyChip
import com.example.profildeveloppeurcarlos.ui.theme.ProfilDeveloppeurCarlosTheme

// Écran principal, STATELESS : il ne stocke pas l'état principal et ne connaît pas le ViewModel.
// Il reçoit les valeurs à afficher en paramètres et remonte chaque action par un callback.
// Une LazyColumn empile toutes les sections et fait défiler la page.
@Composable
fun ProfileScreen(
    profile: Profile,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    showOnlyFavorites: Boolean,
    onToggleFavoriteFilter: () -> Unit,
    favoriteProjectIds: Set<Int>,
    onFavoriteClick: (Int) -> Unit,
    visibleProjects: List<Project>,
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        item {
            ProfileHeader(
                name = profile.name,
                role = profile.role,
                description = profile.description,
                isAvailable = profile.isAvailable,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            SectionTitle(
                title = "Compétences",
                icon = Icons.Filled.Build,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        item {
            // LazyRow : les compétences défilent horizontalement.
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(profile.skills) { skill ->
                    TechnologyChip(name = skill)
                }
            }
        }

        item {
            SectionTitle(
                title = "Projets",
                icon = Icons.AutoMirrored.Filled.List,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        item {
            ProjectSearchField(
                query = searchQuery,
                onQueryChange = onSearchQueryChange
            )
        }
        item {
            ProjectFilterBar(
                showOnlyFavorites = showOnlyFavorites,
                onToggleFavoriteFilter = onToggleFavoriteFilter,
                visibleProjectCount = visibleProjects.size,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (visibleProjects.isEmpty()) {
            item {
                EmptyProjectsMessage(
                    message = emptyProjectsMessage(searchQuery, showOnlyFavorites),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            // key : l'id identifie chaque projet pour que Compose suive correctement les éléments de la liste.
            items(visibleProjects, key = { it.id }) { project ->
                ProjectCard(
                    project = project,
                    isFavorite = project.id in favoriteProjectIds,
                    onFavoriteClick = { onFavoriteClick(project.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            SectionTitle(
                title = "Contact",
                icon = Icons.Filled.Person,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        item {
            // ContactSection garde son propre état local (zone ouverte ou fermée).
            ContactSection(
                email = profile.email,
                location = profile.location,
                isAvailable = profile.isAvailable,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            ProfileActions(
                onContactClick = onContactClick,
                onPortfolioClick = onPortfolioClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Message adapté à la raison pour laquelle la liste est vide.
private fun emptyProjectsMessage(searchQuery: String, showOnlyFavorites: Boolean): String {
    val query = searchQuery.trim()
    return when {
        query.isNotEmpty() && showOnlyFavorites -> "Aucun projet favori ne correspond à « $query »."
        query.isNotEmpty() -> "Aucun projet ne correspond à « $query »."
        else -> "Aucun projet en favori. Touchez le cœur d'un projet pour l'ajouter."
    }
}

// Les previews utilisent directement ProfileScreen, sans ViewModel : l'état est écrit à la main.
// visibleProjects est calculé avec la même fonction filterProjects() que le ViewModel,
// pour que chaque preview montre exactement ce que l'application afficherait dans cet état.
@Composable
private fun ProfileScreenPreviewContent(
    profile: Profile,
    searchQuery: String = "",
    showOnlyFavorites: Boolean = false,
    favoriteProjectIds: Set<Int> = emptySet()
) {
    ProfilDeveloppeurCarlosTheme {
        // Surface : applique la couleur de fond du thème, comme le Scaffold dans l'app.
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen(
                profile = profile,
                searchQuery = searchQuery,
                onSearchQueryChange = {},
                showOnlyFavorites = showOnlyFavorites,
                onToggleFavoriteFilter = {},
                favoriteProjectIds = favoriteProjectIds,
                onFavoriteClick = {},
                visibleProjects = filterProjects(
                    projects = profile.projects,
                    searchQuery = searchQuery,
                    showOnlyFavorites = showOnlyFavorites,
                    favoriteProjectIds = favoriteProjectIds
                ),
                onContactClick = {},
                onPortfolioClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// Preview 1 : recherche vide, tous les projets affichés (4), aucun favori.
@Preview(name = "1. État initial", showBackground = true, heightDp = 1700)
@Composable
fun ProfileScreenInitialPreview() {
    ProfileScreenPreviewContent(profile = carlosProfile)
}

// Preview 2 : recherche « TypeScript », filtre favoris activé, 3 favoris (projets 1, 2 et 4).
// Le projet 4 est favori mais n'utilise pas TypeScript : il est masqué, il reste 2 projets affichés.
@Preview(name = "2. Recherche + favoris", showBackground = true, heightDp = 1300)
@Composable
fun ProfileScreenSearchAndFavoritesPreview() {
    ProfileScreenPreviewContent(
        profile = carlosProfile,
        searchQuery = "TypeScript",
        showOnlyFavorites = true,
        favoriteProjectIds = setOf(1, 2, 4)
    )
}

// Même état que la preview 2, en thème sombre.
// uiMode = nuit : isSystemInDarkTheme() renvoie true, le thème applique donc DarkColorScheme.
@Preview(
    name = "3. Sombre",
    showBackground = true,
    heightDp = 1300,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenDarkPreview() {
    ProfileScreenPreviewContent(
        profile = carlosProfile,
        searchQuery = "TypeScript",
        showOnlyFavorites = true,
        favoriteProjectIds = setOf(1, 2, 4)
    )
}

// Recherche sans résultat : le message vide remplace les cartes et le compteur affiche 0.
@Preview(name = "4. Aucun résultat", showBackground = true, heightDp = 1100)
@Composable
fun ProfileScreenNoResultPreview() {
    ProfileScreenPreviewContent(
        profile = carlosProfile,
        searchQuery = "Swift"
    )
}

// Même écran avec un autre profil : montre que les composants sont réutilisables
// et affiche l'état « non disponible » (pastille rouge).
@Preview(name = "5. Non disponible", showBackground = true, heightDp = 1400)
@Composable
fun ProfileScreenUnavailablePreview() {
    ProfileScreenPreviewContent(profile = thomasProfile)
}
