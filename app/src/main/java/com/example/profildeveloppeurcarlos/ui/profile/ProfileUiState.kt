package com.example.profildeveloppeurcarlos.ui.profile

import com.example.profildeveloppeurcarlos.model.Profile
import com.example.profildeveloppeurcarlos.model.Project

// État principal de l'écran de profil, géré par le ViewModel et affiché par ProfileScreen.
data class ProfileUiState(
    // Données du développeur : nom, rôle, compétences, contact et liste complète des projets.
    val profile: Profile,
    // Texte saisi dans le champ de recherche.
    val searchQuery: String = "",
    // true : seuls les projets favoris sont affichés.
    val showOnlyFavorites: Boolean = false,
    // Identifiants des projets favoris. Un Set ne peut pas contenir deux fois le même id.
    val favoriteProjectIds: Set<Int> = emptySet(),
    // Projets réellement affichés, après la recherche et le filtre favoris.
    val visibleProjects: List<Project> = profile.projects
)

// Fonction pure : le même résultat pour les mêmes paramètres, sans dépendre de Compose ni du ViewModel.
// Le ViewModel l'utilise pour calculer visibleProjects, et les previews pour simuler un état filtré.
fun filterProjects(
    projects: List<Project>,
    searchQuery: String,
    showOnlyFavorites: Boolean,
    favoriteProjectIds: Set<Int>
): List<Project> {
    val query = searchQuery.trim()

    return projects.filter { project ->
        // Recherche dans le titre, la description, l'année et les technologies, sans tenir compte des majuscules.
        val matchesSearch = query.isEmpty() ||
            project.title.contains(query, ignoreCase = true) ||
            project.description.contains(query, ignoreCase = true) ||
            project.year.contains(query) ||
            project.technologies.any { technology -> technology.contains(query, ignoreCase = true) }

        val matchesFavorites = !showOnlyFavorites || project.id in favoriteProjectIds

        matchesSearch && matchesFavorites
    }
}
