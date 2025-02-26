package com.surgery

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.surgery.domain.model.Phase
import com.surgery.procedures_list.comp.PhaseItem
import org.junit.Rule
import org.junit.Test

class PhaseItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysImageAndText() {
        val testPhase = Phase(
            icon = "https://example.com/test_image.png",
            name = "Test Phase",
            uuid = "uuid",
        )

        composeTestRule.setContent {
            PhaseItem(phase = testPhase)
        }

        composeTestRule.onNode(
            hasContentDescription("Phase Image"),
        ).assertExists()

        composeTestRule.onNodeWithText("Test Phase").assertExists()
    }
}
