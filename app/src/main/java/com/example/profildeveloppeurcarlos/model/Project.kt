package com.example.profildeveloppeurcarlos.model

// Un projet ou une expérience affiché dans la liste du profil.
// id : identifiant unique, utilisé pour les favoris et comme clé de la LazyColumn.
data class Project(
    val id: Int,
    val title: String,
    val description: String,
    val technologies: List<String>,
    val year: String
)
