package com.surgery

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.surgery.domain.model.Procedure
import com.surgery.procedures_list.comp.ProcedureListCard
import kotlinx.datetime.LocalDateTime
import org.junit.Rule
import org.junit.Test

class ProcedureListCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysProcedureDetailsAndHandlesClicks() {
        val testProcedure = Procedure(
            icon = "https://example.com/test_procedure.png",
            name = "Test Procedure",
            phasesCount = 3,
            isFavorite = false,
            creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
            duration = 60,
            phases = emptyList(),
            uuid = "uuid",
        )

        var cardClicked = false
        var favoriteToggled = false

        composeTestRule.setContent {
            ProcedureListCard(
                procedure = testProcedure,
                onClick = { cardClicked = true },
                onFavoriteToggle = { favoriteToggled = !favoriteToggled },
            )
        }

        composeTestRule.onNodeWithText("Test Procedure").assertExists()

        composeTestRule.onNodeWithText("Phases: 3").assertExists()

        composeTestRule.onNode(hasContentDescription("Favorite")).performClick()
        assert(favoriteToggled)

        composeTestRule.onNodeWithText("Test Procedure").performClick()
        assert(cardClicked)
    }
}
