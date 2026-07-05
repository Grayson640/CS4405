package com.example.unit3

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "issues")
data class Issue (
    @PrimaryKey(true)
    @ColumnInfo(name = "issueID")
    val id: Int = 0,

    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val creationDate: String
)