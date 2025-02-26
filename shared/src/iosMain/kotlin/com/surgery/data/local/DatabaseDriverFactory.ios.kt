package com.surgery.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.surgery.db.SurgeryDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(SurgeryDatabase.Schema, "SurgeryDatabase.db")
    }
}