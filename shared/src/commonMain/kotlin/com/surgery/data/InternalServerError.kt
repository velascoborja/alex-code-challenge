package com.surgery.data

data class InternalServerError(val status: Int, val description: String) : Exception()