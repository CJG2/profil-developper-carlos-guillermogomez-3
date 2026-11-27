package com.example.profildeveloppeurcarlos.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeurcarlos.data.thomasProfile
import com.example.profildeveloppeurcarlos.ui.theme.ProfilDeveloppeurCarlosTheme

// Carte de contact repliable. Composable STATEFUL : il garde lui-même l'état « ouvert / fermé ».
// Cet état ne concerne que l'affichage de cette zone : ni le reste de l'écran ni le ViewModel
// n'en ont besoin, il reste donc local. rememberSaveable le conserve après une rotation de l'écran.
@Composable
fun ContactSection(
    email: String,
    location: String,
    isAvailable: Boolean,
    modifier: Modifier = Modifier,
    // Valeur de départ uniquement : permet aux previews d'afficher la zone déjà ouverte.
    initiallyExpanded: Boolean = false
) {
    var isExpanded by rememberSaveable { mutableStateOf(initiallyExpanded) }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            TextButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isExpanded) {
                        "Masquer les informations de contact"
                    } else {
                        "Afficher les informations de contact"
                    }
                )
            }

            if (isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)
                ) {
                    ContactField(
                        label = "E-mail",
                        value = email,
                        icon = Icons.Filled.Email
                    )
                    ContactField(
                        label = "Localisation",
                        value = location,
                        icon = Icons.Filled.LocationOn
                    )
                    ContactField(
                        label = "Disponibilité",
                        value = if (isAvailable) {
                            "Disponible pour de nouveaux projets"
                        } else {
                            "Non disponible actuellement"
                        },
                        icon = if (isAvailable) Icons.Filled.CheckCircle else Icons.Filled.Info
                    )
                }
            }
        }
    }
}

// readOnly = true : le champ s'affiche comme un formulaire mais ne peut pas être modifié,
// donc onValueChange n'a rien à faire.
@Composable
fun ContactField(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)
        },
        modifier = modifier.fillMaxWidth()
    )
}

// Les deux états de la zone : fermée (au démarrage) et ouverte.
@Preview(showBackground = true)
@Composable
fun ContactSectionPreview() {
    ProfilDeveloppeurCarlosTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            ContactSection(
                email = thomasProfile.email,
                location = thomasProfile.location,
                isAvailable = thomasProfile.isAvailable,
                modifier = Modifier.fillMaxWidth()
            )
            ContactSection(
                email = thomasProfile.email,
                location = thomasProfile.location,
                isAvailable = thomasProfile.isAvailable,
                initiallyExpanded = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
