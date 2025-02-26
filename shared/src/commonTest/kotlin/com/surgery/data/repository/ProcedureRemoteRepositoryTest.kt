package com.surgery.data.repository

import app.cash.turbine.test
import com.surgery.data.local.Database
import com.surgery.data.mapper.toDomain
import com.surgery.data.mapper.toEntity
import com.surgery.data.model.BannerDto
import com.surgery.data.model.CardDto
import com.surgery.data.model.ChannelDto
import com.surgery.data.model.IconDto
import com.surgery.data.model.PhaseDto
import com.surgery.data.model.ProcedureDetailsDto
import com.surgery.data.model.ProcedureDto
import com.surgery.data.remote.ProcedureSource
import com.surgery.db.ProcedureEntity
import com.surgery.domain.model.Procedure
import com.surgery.domain.repository.ProcedureRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProcedureRemoteRepositoryTest {

    private val procedureSource: ProcedureSource = mock()
    private val database: Database = mock()
    private val repository: ProcedureRepository =
        ProcedureRemoteRepository(procedureSource, database)

    @Test
    fun testGetProcedureList() = runTest {
        val procedureEntities = listOf(procedureEntity1, procedureEntity2)
        val procedures = procedureEntities.map { it.toDomain() }

        every { database.getProcedures(any()) } returns flowOf(procedureEntities)

        val result: Flow<List<Procedure>> = repository.getProcedureList("")

        result.test {
            assertEquals(procedures, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun testRefreshProcedureList() = runBlocking {
        val procedureDtos = listOf(procedureDto1, procedureDto2)
        val procedureEntities = procedureDtos.map { it.toEntity() }

        everySuspend { procedureSource.getProcedureList() } returns Result.success(procedureDtos)
        everySuspend { database.saveProcedures(any()) } returns Unit

        repository.refreshProcedureList()

        verifySuspend(VerifyMode.exhaustiveOrder) {
            procedureSource.getProcedureList()
            database.saveProcedures(procedureEntities)
        }
    }

    @Test
    fun testGetProcedureDetailsSuccess() = runBlocking {
        val phaseEntities = procedureDetailsDto.phases.map { it.toEntity(procedureDetailsDto.uuid) }

        everySuspend { procedureSource.getProcedureDetails(any()) } returns Result.success(
            procedureDetailsDto
        )
        everySuspend { database.savePhases(any(), any()) } returns Unit

        val result = repository.getProcedureDetails(procedure)

        assertTrue(result.isSuccess)
        assertEquals(phaseEntities.map { it.toDomain() }, result.getOrNull())
    }

    @Test
    fun testGetProcedureDetailsFailure() = runBlocking {
        val exception = Exception("Network error")

        everySuspend { procedureSource.getProcedureDetails(any()) } returns Result.failure(exception)
        everySuspend { database.getPhases(any()) } returns emptyList()

        val result = repository.getProcedureDetails(procedure)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun testToggleProcedureFavouriteState() = runBlocking {
        everySuspend { database.updateProcedureFavouriteState(any(), any()) } returns Unit

        repository.toggleProcedureFavouriteState(procedure)

        verifySuspend { database.updateProcedureFavouriteState(procedure.uuid, false) }
    }

    private companion object {
        val procedureEntity1 = ProcedureEntity(
            uuid = "1",
            name = "Procedure 1",
            icon = "icon1",
            phasesCount = 3,
            duration = 60,
            creationDate = "2015-04-14T10:00:51.940581",
            isFavorite = 0,
        )

        val procedureEntity2 = ProcedureEntity(
            uuid = "2",
            name = "Procedure 2",
            icon = "icon2",
            phasesCount = 2,
            duration = 45,
            creationDate = "2015-04-14T10:00:51.940581",
            isFavorite = 1,
        )

        val procedureDto1 = ProcedureDto(
            uuid = "1",
            name = "Procedure 1",
            icon = IconDto(
                url = "icon1",
                uuid = "uuid",
                version = 1,
            ),
            phases = listOf("1", "2", "3"),
            duration = 60,
            datePublished = "2015-04-14T10:00:51.940581",
            author = "author",
            deepLink = "deeplink",
            doiCode = "doiCode",
            isPurchasable = true,
            labels = emptyList(),
            organisation = "organisation",
            siteSlug = "siteSlug",
            specialties = emptyList(),
        )
        val procedureDto2 = ProcedureDto(
            uuid = "1",
            name = "Procedure 2",
            icon = IconDto(
                url = "icon2",
                uuid = "uuid",
                version = 1,
            ),
            phases = listOf("1", "2", "3", "4"),
            duration = 60,
            datePublished = "2015-04-15T10:00:51.940581",
            author = "author",
            deepLink = "deeplink",
            doiCode = "doiCode",
            isPurchasable = true,
            labels = emptyList(),
            organisation = "organisation",
            siteSlug = "siteSlug",
            specialties = emptyList(),
        )
        val procedure = Procedure(
            uuid = "1",
            name = "Procedure 1",
            creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
            duration = 60,
            icon = "icon1",
            phasesCount = 1,
            isFavorite = true,
            phases = null
        )
        val procedureDetailsDto = ProcedureDetailsDto(
            uuid = "1",
            name = "Procedure 1",
            icon = IconDto(
                url = "icon1",
                uuid = "uuid",
                version = 1,
            ),
            phases = listOf(
                PhaseDto(
                    deepLink = "deepLink",
                    icon = IconDto(
                        url = "url",
                        uuid = "uuid",
                        version = 1,
                    ),
                    learnCompleted = true,
                    name = "name",
                    testMode = true,
                    uuid = "uuid",
                    viewed = true,
                )
            ),
            duration = 60,
            datePublished = "2015-04-14T10:00:51.940581",
            author = "author",
            deepLink = "deeplink",
            doiCode = "doiCode",
            isPurchasable = true,
            organisation = "organisation",
            specialties = emptyList(),
            card = CardDto(
                url = "url",
                uuid = "uuid",
                version = 1
            ),
            channel = ChannelDto(
                banner = BannerDto(
                    url = "url",
                    uuid = "uuis",
                    version = 1,
                )
            ),
            overview = emptyList(),
            viewCount = 1,
        )
    }
}