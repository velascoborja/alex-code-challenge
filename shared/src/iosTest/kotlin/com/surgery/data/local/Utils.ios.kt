package com.surgery.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.surgery.db.SurgeryDatabase

actual fun createTestDbDriver(): SqlDriver {
    val schema = SurgeryDatabase.Schema
    return inMemoryDriver(schema)
}