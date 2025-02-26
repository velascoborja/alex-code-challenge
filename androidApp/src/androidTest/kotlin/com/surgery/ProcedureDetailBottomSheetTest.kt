package com.surgery

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import com.surgery.procedures_list.comp.ProcedureDetailBottomSheet
import kotlinx.datetime.LocalDateTime
import org.junit.Rule
import org.junit.Test

class ProcedureDetailBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysProcedureDetails() {
        val testProcedure = Procedure(
            icon = "https://example.com/test_procedure.png",
            name = "Test Procedure",
            duration = 60,
            creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
            isFavorite = false,
            phases = listOf(
                Phase(uuid = "1", icon = "https://example.com/phase1.png", name = "Phase 1"),
                Phase(uuid = "2", icon = "https://example.com/phase2.png", name = "Phase 2")
            ),
            phasesCount = 2,
            uuid = "uuid",
        )

        var favoriteToggled = false

        composeTestRule.setContent {
            ProcedureDetailBottomSheet(
                procedure = testProcedure,
                onToggleFavorite = { favoriteToggled = !favoriteToggled },
                onDismiss = {}
            )
        }

        composeTestRule.onNodeWithText("Test Procedure").assertExists()

        composeTestRule.onNodeWithText("Duration: 1 minutes").assertExists()

        composeTestRule.onNodeWithText("Created on: 14/04/2015").assertExists()

        composeTestRule.onNodeWithText("Phase 1").assertExists()
        composeTestRule.onNodeWithText("Phase 2").assertExists()

        composeTestRule.onNode(hasContentDescription("Favorite")).performClick()
        assert(favoriteToggled)

        composeTestRule.onNodeWithText("Close").assertExists()
    }
}
