package com.example.unit3

import android.app.Application

class IssueRepository(application: Application) {

    private val issueDao = IssueDatabase
        .getDatabase(application)!!
        .issueDao()

    val allIssues = issueDao.getAllIssues()

    suspend fun insert(issue: Issue) =
        issueDao.addIssue(issue)

    suspend fun update(issue: Issue) =
        issueDao.updateIssue(issue)

    suspend fun delete(issue: Issue) =
        issueDao.deleteIssue(issue)

    suspend fun findById(id: Int) =
        issueDao.findID(id)

    suspend fun loadDefaultIssues() {
        for (issue in DefaultIssues.issues){
            issueDao.addIssue(issue)
        }
    }
}