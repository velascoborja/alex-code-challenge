package com.surgery.data.local

import app.cash.sqldelight.db.SqlDriver

internal expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}