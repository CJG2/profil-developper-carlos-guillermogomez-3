package com.example.profildeveloppeurcarlos.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeurcarlos.data.carlosProfile
import com.example.profildeveloppeurcarlos.model.Project
import com.example.profildeveloppeurcarlos.ui.theme.ProfilDeveloppeurCarlosTheme

// Carte d'un projet : un élément de la liste des projets.
// Stateless : elle reçoit isFavorite et remonte le clic sur le cœur au parent.
@Composable
fun ProjectCard(
    project: Project,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 4.dp, bottom = 16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // weight(1f) : le titre prend toute la place restante, l'année et le cœur restent à droite.
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = project.year,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        // L'action est décrite pour TalkBack, car l'icône seule n'a pas de texte.
                        contentDescription = if (isFavorite) {
                            "Retirer ${project.title} des favoris"
                        } else {
                            "Ajouter ${project.title} aux favoris"
                        },
                        tint = if (isFavorite) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
            Column(modifier = Modifier.padding(end = 12.dp)) {
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (technology in project.technologies) {
                        TechnologyChip(name = technology)
                    }
                }
            }
        }
    }
}

// Les deux états du cœur : favori (plein) et non favori (vide).
@Preview(showBackground = true)
@Composable
fun ProjectCardPreview() {
    ProfilDeveloppeurCarlosTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            ProjectCard(
                project = carlosProfile.projects[0],
                isFavorite = true,
                onFavoriteClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            ProjectCard(
                project = carlosProfile.projects[1],
                isFavorite = false,
                onFavoriteClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
