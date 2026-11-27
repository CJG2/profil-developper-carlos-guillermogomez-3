package com.example.profildeveloppeurcarlos.ui.profile

import com.example.profildeveloppeurcarlos.data.thomasProfile
import com.example.profildeveloppeurcarlos.ui.components.projectCountText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

// Tests du ViewModel, sans émulateur : ils couvrent la liste « À tester » du sujet
// (recherche, ajout et retrait de favori, filtre, nombre de projets, nouvel état publié).
// Projets de carlosProfile : 1 Front-End thecamp (TypeScript, React, Next.js) ;
// 2 Application mobile Expo (React Native, Expo, TypeScript) ; 3 API REST Nest.js (Nest.js, PostgreSQL) ;
// 4 Profil développeur Compose (Kotlin, Jetpack Compose).
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel

    // Un nouveau ViewModel avant chaque test : les tests ne dépendent pas les uns des autres.
    @Before
    fun setUp() {
        viewModel = ProfileViewModel()
    }

    // Raccourci : les id des projets actuellement affichés.
    private fun visibleIds(): List<Int> = viewModel.uiState.value.visibleProjects.map { it.id }

    @Test
    fun initialState_showsAllProjectsWithoutFavorites() {
        val state = viewModel.uiState.value

        assertEquals("", state.searchQuery)
        assertFalse(state.showOnlyFavorites)
        assertTrue(state.favoriteProjectIds.isEmpty())
        assertEquals(listOf(1, 2, 3, 4), visibleIds())
    }

    @Test
    fun search_byTitle_filtersProjects() {
        viewModel.onSearchQueryChange("Expo")

        assertEquals("Expo", viewModel.uiState.value.searchQuery)
        assertEquals(listOf(2), visibleIds())
    }

    @Test
    fun search_byTechnology_ignoresCase() {
        viewModel.onSearchQueryChange("typescript")

        assertEquals(listOf(1, 2), visibleIds())
    }

    @Test
    fun search_byYear_filtersProjects() {
        viewModel.onSearchQueryChange("2024")

        assertEquals(listOf(2, 3), visibleIds())
    }

    @Test
    fun search_withoutMatch_showsNoProject() {
        viewModel.onSearchQueryChange("Swift")

        assertTrue(visibleIds().isEmpty())
    }

    @Test
    fun clearingSearch_showsAllProjectsAgain() {
        viewModel.onSearchQueryChange("Swift")
        viewModel.onSearchQueryChange("")

        assertEquals(listOf(1, 2, 3, 4), visibleIds())
    }

    @Test
    fun favoriteClick_addsProjectToFavorites() {
        viewModel.onFavoriteClick(2)

        assertEquals(setOf(2), viewModel.uiState.value.favoriteProjectIds)
    }

    @Test
    fun favoriteClickTwice_removesProjectFromFavorites() {
        viewModel.onFavoriteClick(2)
        viewModel.onFavoriteClick(2)

        assertTrue(viewModel.uiState.value.favoriteProjectIds.isEmpty())
    }

    @Test
    fun favoriteFilter_showsOnlyFavoriteProjects() {
        viewModel.onFavoriteClick(1)
        viewModel.onFavoriteClick(3)
        viewModel.onToggleFavoriteFilter()

        assertTrue(viewModel.uiState.value.showOnlyFavorites)
        assertEquals(listOf(1, 3), visibleIds())
    }

    @Test
    fun favoriteFilter_toggledTwice_showsAllProjectsAgain() {
        viewModel.onFavoriteClick(1)
        viewModel.onToggleFavoriteFilter()
        viewModel.onToggleFavoriteFilter()

        assertFalse(viewModel.uiState.value.showOnlyFavorites)
        assertEquals(listOf(1, 2, 3, 4), visibleIds())
    }

    @Test
    fun removingFavorite_withFilterActive_hidesProjectImmediately() {
        viewModel.onFavoriteClick(1)
        viewModel.onFavoriteClick(3)
        viewModel.onToggleFavoriteFilter()

        viewModel.onFavoriteClick(3)

        assertEquals(listOf(1), visibleIds())
    }

    @Test
    fun searchAndFavoriteFilter_areCombined() {
        viewModel.onFavoriteClick(1)
        viewModel.onFavoriteClick(2)
        viewModel.onFavoriteClick(4)
        viewModel.onToggleFavoriteFilter()

        viewModel.onSearchQueryChange("TypeScript")

        // Le projet 4 est favori mais n'utilise pas TypeScript.
        assertEquals(listOf(1, 2), visibleIds())
    }

    @Test
    fun visibleProjectCount_followsSearchAndFilter() {
        assertEquals("4 projets affichés", projectCountText(visibleIds().size))

        viewModel.onSearchQueryChange("TypeScript")
        assertEquals("2 projets affichés", projectCountText(visibleIds().size))

        viewModel.onFavoriteClick(2)
        viewModel.onToggleFavoriteFilter()
        assertEquals("1 projet affiché", projectCountText(visibleIds().size))

        viewModel.onFavoriteClick(2)
        assertEquals("0 projet affiché", projectCountText(visibleIds().size))
    }

    @Test
    fun action_publishesNewStateOnStateFlow() {
        val stateBefore = viewModel.uiState.value

        viewModel.onSearchQueryChange("React")
        val stateAfter = viewModel.uiState.value

        // Le StateFlow publie un nouvel objet d'état : c'est ce changement que collectAsState() détecte.
        assertNotSame(stateBefore, stateAfter)
        assertEquals("React", stateAfter.searchQuery)
        // L'ancien état n'est pas modifié : chaque action crée une copie avec copy().
        assertEquals("", stateBefore.searchQuery)
    }

    @Test
    fun viewModel_worksWithAnotherProfile() {
        val thomasViewModel = ProfileViewModel(profile = thomasProfile)

        thomasViewModel.onSearchQueryChange("Docker")

        assertEquals("Thomas Durand", thomasViewModel.uiState.value.profile.name)
        assertEquals(listOf(6), thomasViewModel.uiState.value.visibleProjects.map { it.id })
    }
}
