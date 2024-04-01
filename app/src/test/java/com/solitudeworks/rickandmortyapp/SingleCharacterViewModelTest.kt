package com.solitudeworks.rickandmortyapp

import com.solitudeworks.rickandmortyapp.data.domain.GetSingleCharactersUseCase
import com.solitudeworks.rickandmortyapp.data.model.CharacterDetail
import com.solitudeworks.rickandmortyapp.ui.presentation.viewmodel.SingleCharacterViewModel
import com.solitudeworks.rickandmortyapp.utils.LoadingState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SingleCharacterViewModelTest {
    @Test
    fun `When retuning a Character by ID, verify if it is equal the MockCharacter`() {
        val mockCharacterId = 1

        val mockCharacter =
            CharacterDetail(
                created = "2017-11-04T18:48:46.250Z",
                episode =
                    listOf(
                        "https://rickandmortyapi.com/api/episode/1",
                    ),
                gender = "Male",
                id = mockCharacterId,
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                location = null,
                name = "Rick Sanchez",
                origin = null,
                species = "Human",
                status = "Alive",
                type = "",
                url = "https://rickandmortyapi.com/api/character/1",
            )

        val mockUseCase = mockk<GetSingleCharactersUseCase>()

        val viewModel =
            SingleCharacterViewModel(mockUseCase).apply {
                characterId = mockCharacterId
            }

        // Verify initial loading state
        assertEquals(viewModel.loadingState.value, LoadingState.Loading)

        coEvery { mockUseCase.getSingleCharacterUseCase(1) } returns mockCharacter

        viewModel.getSingleCharacter()

        // Seeing if the state load is ready in the view model
        runBlockingTest {
            assertEquals(viewModel.loadingState.value, LoadingState.Ready)
        }

        // Seeing if the return value is equal the mock character
        runBlockingTest {
            assertEquals(viewModel.singleCharacter.value, mockCharacter)
        }
    }

    @Test
    fun `When retuning a CharacterOne by ID, verify if it is not equal CharacterTwo`() {
        val mockCharacterOneId = 1

        val mockCharacterOne =
            CharacterDetail(
                created = "2017-11-04T18:48:46.250Z",
                episode =
                    listOf(
                        "https://rickandmortyapi.com/api/episode/1",
                    ),
                gender = "Male",
                id = mockCharacterOneId,
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                location = null,
                name = "Rick Sanchez",
                origin = null,
                species = "Human",
                status = "Alive",
                type = "",
                url = "https://rickandmortyapi.com/api/character/1",
            )

        val mockCharacterTwoId = 2

        val mockCharacterTwo =
            CharacterDetail(
                created = "2017-11-04T18:50:21.651Z",
                episode =
                    listOf(
                        "https://rickandmortyapi.com/api/episode/1",
                    ),
                gender = "Male",
                id = mockCharacterTwoId,
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                location = null,
                name = "Morty Smith",
                origin = null,
                species = "Human",
                status = "Alive",
                type = "",
                url = "https://rickandmortyapi.com/api/character/2",
            )

        val mockUseCase = mockk<GetSingleCharactersUseCase>()

        val viewModel =
            SingleCharacterViewModel(mockUseCase).apply {
                characterId = mockCharacterOneId
            }

        // Verify initial loading state
        assertEquals(viewModel.loadingState.value, LoadingState.Loading)

        coEvery { mockUseCase.getSingleCharacterUseCase(1) } returns mockCharacterOne

        viewModel.getSingleCharacter()

        // Seeing if the state load is ready in the view model
        runBlockingTest {
            assertEquals(viewModel.loadingState.value, LoadingState.Ready)
        }

        // Seeing if the return value is equal the second mock character
        runBlockingTest {
            assertNotEquals(viewModel.singleCharacter.value, mockCharacterTwo)
        }
    }
}
