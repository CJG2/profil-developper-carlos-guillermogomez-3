package com.example.profildeveloppeurcarlos.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeurcarlos.ui.theme.ProfilDeveloppeurCarlosTheme

// Ligne de filtre : le bouton « Favoris uniquement » à gauche, le nombre de projets affichés à droite.
// Stateless : elle reçoit l'état du filtre et le nombre de projets, et remonte le clic au parent.
@Composable
fun ProjectFilterBar(
    showOnlyFavorites: Boolean,
    onToggleFavoriteFilter: () -> Unit,
    visibleProjectCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // FilterChip : un bouton à deux états, sélectionné ou non.
        FilterChip(
            selected = showOnlyFavorites,
            onClick = onToggleFavoriteFilter,
            label = { Text(text = "Favoris uniquement") },
            leadingIcon = {
                Icon(
                    imageVector = if (showOnlyFavorites) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        )
        Text(
            text = projectCountText(visibleProjectCount),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

// En français, 0 et 1 sont au singulier : « 0 projet affiché », « 1 projet affiché », « 3 projets affichés ».
fun projectCountText(count: Int): String {
    return if (count <= 1) {
        "$count projet affiché"
    } else {
        "$count projets affichés"
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectFilterBarPreview() {
    ProfilDeveloppeurCarlosTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ProjectFilterBar(
                showOnlyFavorites = false,
                onToggleFavoriteFilter = {},
                visibleProjectCount = 4,
                modifier = Modifier.fillMaxWidth()
            )
            ProjectFilterBar(
                showOnlyFavorites = true,
                onToggleFavoriteFilter = {},
                visibleProjectCount = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
