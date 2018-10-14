package com.abhat.wiki.wikiviewmodel

import com.abhat.wiki.data.repository.WikiRepositoryImpl
import com.abhat.wiki.presentation.WikiViewModel
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class WikiViewModelTest {

    private lateinit var wikiViewModel: WikiViewModel

    lateinit var mockWikiRepository: WikiRepositoryImpl

    @Before
    fun setup() {
        mockWikiRepository = mock()
        wikiViewModel = WikiViewModel(mockWikiRepository)
    }

    @Test
    fun `returns search term first letter capitalized for each word` () {
        Assert.assertTrue(assertSearchTerm("narendra modi") == "Narendra Modi")
        Assert.assertTrue(assertSearchTerm("karnataka") == "Karnataka")
        Assert.assertTrue(assertSearchTerm("this is some long text") == "This Is Some Long Text")
    }

    private fun assertSearchTerm(searchTerm: String): String {
        return wikiViewModel.cleanSearchTerm(searchTerm)
    }
}