package com.example.unit3

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "issues")
data class Issue (
    @PrimaryKey(true)
    @ColumnInfo(name = "issueID")
    val id: Int = 0,

    // remoteId is assigned a value after successful online backup
    val remoteId: String? = null,

    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val creationDate: String,

    // Signals whether this entry has been uploaded
    val synced: Boolean = false
)