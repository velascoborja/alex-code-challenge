package com.surgery.data.local

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.turbine.test
import com.surgery.db.PhaseEntity
import com.surgery.db.ProcedureEntity
import com.surgery.db.SurgeryDatabase
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SqlDelightDatabaseTest {
    private val driver = createTestDbDriver()
    private val database = SurgeryDatabase(
        driver = driver,
        ProcedureEntityAdapter = ProcedureEntity.Adapter(IntColumnAdapter, IntColumnAdapter),
    )
    private val dbHelper = SqlDelightDatabase(database)

    @BeforeTest
    fun setUp() {
        SurgeryDatabase.Schema.create(driver)
    }

    @AfterTest
    fun resetDriver() {
        driver.close()
    }

    @Test
    fun testSaveAndRetrieveProcedures() = runBlocking {
        dbHelper.saveProcedures(listOf(procedureEntity1))
        dbHelper.getProcedures("").test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(procedureEntity1, result.first())
            expectNoEvents()
        }
    }

    @Test
    fun testSaveAndRetrievePhases() = runBlocking {
        dbHelper.savePhases(
            procedureId = procedureEntity1.uuid,
            phaseEntities = listOf(phaseEntity),
        )
        val phases = dbHelper.getPhases(procedureEntity1.uuid)
        assertEquals(1, phases.size)
        assertEquals("Phase 1", phases[0].name)
    }

    @Test
    fun testUpdateProcedureFavouriteState() = runBlocking {
        dbHelper.saveProcedures(listOf(procedureEntity1, procedureEntity2))
        dbHelper.updateProcedureFavouriteState(
            procedureId = procedureEntity2.uuid,
            isFavourite = true,
        )
        dbHelper.getFavouriteProcedures("").test {
            val result = awaitItem()
            assertEquals(procedureEntity2.copy(isFavorite = true), result.first())
        }
    }

    private companion object {
        val procedureEntity1 = ProcedureEntity(
            uuid = "procedure1",
            name = "Procedure 1",
            icon = "icon1",
            phasesCount = 3,
            duration = 60,
            creationDate = "2015-04-14T10:00:51.940581",
            isFavorite = false,
        )

        val procedureEntity2 = ProcedureEntity(
            uuid = "procedure2",
            name = "Procedure 2",
            icon = "icon1",
            phasesCount = 3,
            duration = 60,
            creationDate = "2015-04-14T10:00:51.940581",
            isFavorite = false,
        )

        val phaseEntity = PhaseEntity(
            uuid = "phase1",
            procedureUuid = "procedure1",
            name = "Phase 1",
            icon = "icon1"
        )
    }
}