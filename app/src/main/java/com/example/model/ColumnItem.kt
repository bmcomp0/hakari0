package com.example.model

import java.util.*

data class ColumnItem (
    val id: UUID = UUID.randomUUID(),
    val value: String = ""
)