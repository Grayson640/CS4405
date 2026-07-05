package com.example.unit3

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao {
    @Query("SELECT * FROM issues")
    fun getAllIssues(): Flow<List<Issue>>

    @Query("SELECT * FROM issues WHERE issueID = :id")
    suspend fun findID(id: Int): Issue?

    @Insert
    suspend fun addIssue(issue: Issue)

    @Query("DELETE FROM issues WHERE issueID = :id")
    suspend fun deleteIssue(id: Int)

    @Delete
    suspend fun deleteIssue(issue: Issue)

    @Update
    suspend fun updateIssue(issue: Issue)
}