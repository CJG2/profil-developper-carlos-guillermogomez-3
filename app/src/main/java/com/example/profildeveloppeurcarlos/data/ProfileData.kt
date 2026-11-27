package com.example.profildeveloppeurcarlos.data

import com.example.profildeveloppeurcarlos.model.Profile
import com.example.profildeveloppeurcarlos.model.Project

// Données locales écrites directement dans le code : pas d'API ni de base de données.

val carlosProfile = Profile(
    name = "Carlos Guillermo Gómez",
    role = "Développeur Web & Mobile",
    description = "Tout juste diplômé d'un BUT Informatique, passionné par le " +
        "développement d'applications modernes et innovantes. " +
        "Développeur Front-End chez thecamp depuis septembre 2025.",
    email = "carlos.guillermogomez18@gmail.com",
    // À adapter
    location = "Aix-en-Provence, France",
    isAvailable = true,
    skills = listOf(
        "TypeScript",
        "React / Next.js",
        "React Native",
        "Node.js / Nest.js",
        "PostgreSQL",
        "Expo",
        "Jetpack Compose"
    ),
    // À adapter avec tes vrais projets
    projects = listOf(
        Project(
            id = 1,
            title = "Front-End chez thecamp",
            description = "Développement d'interfaces web pour les outils internes et " +
                "les événements de thecamp.",
            technologies = listOf("TypeScript", "React", "Next.js"),
            year = "2025"
        ),
        Project(
            id = 2,
            title = "Application mobile Expo",
            description = "Application React Native multiplateforme développée " +
                "pendant le BUT Informatique.",
            technologies = listOf("React Native", "Expo", "TypeScript"),
            year = "2024"
        ),
        Project(
            id = 3,
            title = "API REST Nest.js",
            description = "API avec authentification et base de données relationnelle " +
                "pour un projet tuteuré.",
            technologies = listOf("Nest.js", "PostgreSQL"),
            year = "2024"
        ),
        Project(
            id = 4,
            title = "Profil développeur Compose",
            description = "Cette application : fiche de profil réalisée en Kotlin avec " +
                "Jetpack Compose et Material 3.",
            technologies = listOf("Kotlin", "Jetpack Compose"),
            year = "2026"
        )
    )
)

// Second profil fictif, utilisé dans les previews pour montrer l'état « non disponible ».
val thomasProfile = Profile(
    name = "Thomas Durand",
    role = "Développeur full-stack",
    description = "Cinq ans d'expérience sur des applications web et mobiles, " +
        "actuellement en mission.",
    email = "thomas.durand@exemple.fr",
    location = "Lyon, France",
    isAvailable = false,
    skills = listOf("TypeScript", "React", "Node.js", "PostgreSQL", "Docker"),
    projects = listOf(
        Project(
            id = 5,
            title = "Plateforme e-commerce",
            description = "Refonte du tunnel de commande et du back-office.",
            technologies = listOf("React", "Node.js", "PostgreSQL"),
            year = "2025"
        ),
        Project(
            id = 6,
            title = "Outil de suivi de livraisons",
            description = "Application interne de suivi en temps réel des livreurs.",
            technologies = listOf("TypeScript", "Docker"),
            year = "2023"
        )
    )
)
