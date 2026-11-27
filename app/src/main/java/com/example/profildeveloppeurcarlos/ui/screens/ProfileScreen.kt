package com.example.profildeveloppeurcarlos.ui.screens

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
import com.example.profildeveloppeurcarlos.ui.components.ContactSection
import com.example.profildeveloppeurcarlos.ui.components.ProfileActions
import com.example.profildeveloppeurcarlos.ui.components.ProfileHeader
import com.example.profildeveloppeurcarlos.ui.components.ProjectCard
import com.example.profildeveloppeurcarlos.ui.components.SectionTitle
import com.example.profildeveloppeurcarlos.ui.components.TechnologyChip
import com.example.profildeveloppeurcarlos.ui.theme.ProfilDeveloppeurCarlosTheme

// Écran principal : une LazyColumn qui empile toutes les sections et fait défiler la page.
// Chaque item() est une section, items() affiche un ProjectCard par projet.
@Composable
fun ProfileScreen(
    profile: Profile,
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
        // key : l'id identifie chaque projet pour que Compose suive correctement les éléments de la liste.
        items(profile.projects, key = { it.id }) { project ->
            // Temporaire : les favoris seront branchés au ViewModel à l'étape 6.
            ProjectCard(
                project = project,
                isFavorite = false,
                onFavoriteClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            SectionTitle(
                title = "Contact",
                icon = Icons.Filled.Person,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        item {
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

// Les previews d'écran partagent ce contenu : seuls le profil et le mode (clair/sombre) changent.
@Composable
private fun ProfileScreenPreviewContent(profile: Profile) {
    ProfilDeveloppeurCarlosTheme {
        // Surface : applique la couleur de fond du thème, comme le Scaffold dans l'app.
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen(
                profile = profile,
                onContactClick = {},
                onPortfolioClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(name = "Clair", showBackground = true, heightDp = 1600)
@Composable
fun ProfileScreenLightPreview() {
    ProfileScreenPreviewContent(profile = carlosProfile)
}

// uiMode = nuit : isSystemInDarkTheme() renvoie true, le thème applique donc DarkColorScheme.
@Preview(
    name = "Sombre",
    showBackground = true,
    heightDp = 1600,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenDarkPreview() {
    ProfileScreenPreviewContent(profile = carlosProfile)
}

// Même écran avec un autre profil : montre que les composants sont réutilisables
// et affiche l'état « non disponible » (pastille rouge, champ Disponibilité).
@Preview(name = "Non disponible", showBackground = true, heightDp = 1400)
@Composable
fun ProfileScreenUnavailablePreview() {
    ProfileScreenPreviewContent(profile = thomasProfile)
}
