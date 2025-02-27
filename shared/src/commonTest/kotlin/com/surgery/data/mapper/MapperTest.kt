package com.surgery.data.mapper

import com.surgery.data.model.IconDto
import com.surgery.data.model.PhaseDto
import com.surgery.data.model.ProcedureDto
import com.surgery.db.PhaseEntity
import com.surgery.db.ProcedureEntity
import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun testProcedureDtoToEntity() {
        val procedureDto = ProcedureDto(
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

        val expectedEntity = ProcedureEntity(
            uuid = "1",
            name = "Procedure 1",
            icon = "icon1",
            phasesCount = 3,
            duration = 60,
            creationDate = "2015-04-14T10:00:51.940581",
            isFavorite = false,
        )

        assertEquals(expectedEntity, procedureDto.toEntity())
    }

    @Test
    fun testPhaseDtoToEntity() {
        val phaseDto = PhaseDto(
            uuid = "1",
            name = "Phase 1",
            icon = IconDto(
                url = "icon1",
                uuid = "uuid",
                version = 1,
            ),
            deepLink = "deepLink",
            learnCompleted = true,
            testMode = true,
            viewed = true,
        )

        val expectedEntity = PhaseEntity(
            uuid = "1",
            procedureUuid = "procedure1",
            name = "Phase 1",
            icon = "icon1"
        )

        assertEquals(expectedEntity, phaseDto.toEntity("procedure1"))
    }

    @Test
    fun testProcedureEntityToDomain() {
        val procedureEntity = ProcedureEntity(
            uuid = "1",
            name = "Procedure 1",
            icon = "icon1",
            phasesCount = 1,
            duration = 60,
            creationDate = "2015-04-14T10:00:51.940581",
            isFavorite = true,
        )

        val expectedDomain = Procedure(
            uuid = "1",
            name = "Procedure 1",
            creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
            duration = 60,
            icon = "icon1",
            phasesCount = 1,
            isFavorite = true,
            phases = null,
        )

        assertEquals(expectedDomain, procedureEntity.toDomain())
    }

    @Test
    fun testPhaseEntityToDomain() {
        val phaseEntity = PhaseEntity(
            uuid = "1",
            name = "Phase 1",
            icon = "icon1",
            procedureUuid = "procedure1",
        )

        val expectedDomain = Phase(
            uuid = "1",
            name = "Phase 1",
            icon = "icon1",
        )

        assertEquals(expectedDomain, phaseEntity.toDomain())
    }

    @Test
    fun testProcedureToEntity() {
        val procedure = Procedure(
            uuid = "1",
            name = "Procedure 1",
            creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
            duration = 60,
            icon = "icon1",
            phasesCount = 1,
            isFavorite = true,
            phases = null,
        )

        val expectedEntity = ProcedureEntity(
            uuid = "1",
            name = "Procedure 1",
            creationDate = "2015-04-14T10:00:51.940581",
            duration = 60,
            icon = "icon1",
            phasesCount = 1,
            isFavorite = true,
        )

        assertEquals(expectedEntity, procedure.toEntity())
    }
}