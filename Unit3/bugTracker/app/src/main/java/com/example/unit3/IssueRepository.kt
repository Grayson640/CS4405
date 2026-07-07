package com.example.unit3

import android.app.Application
import com.example.unit3.network.RetrofitClient
import com.example.unit3.network.toRemote

class IssueRepository(application: Application) {

    private val api = RetrofitClient.api
    private val issueDao = IssueDatabase
        .getDatabase(application)!!
        .issueDao()

    val allIssues = issueDao.getAllIssues()

    suspend fun insert(issue: Issue) {

        // Save locally first
        val localId = issueDao.addIssue(issue)

        // Retrieve the saved issue (it now has its Room ID)
        val localIssue = issueDao.findID(localId.toInt()) ?: return

        try {
            // Upload to MockAPI
            val response = api.addIssue(localIssue.toRemote())

            // Save the remote ID back into Room
            issueDao.updateIssue(
                localIssue.copy(remoteId = response.id)
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun update(issue: Issue) {

        issueDao.updateIssue(issue)

        try {
            issue.remoteId?.let {
                api.updateIssue(it, issue.toRemote())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun delete(issue: Issue) {

        issueDao.deleteIssue(issue)

        try {
            issue.remoteId?.let {
                api.deleteIssue(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun findById(id: Int) =
        issueDao.findID(id)

    suspend fun loadDefaultIssues() {
        for (issue in DefaultIssues.issues) {
            issueDao.addIssue(issue)
        }
    }

}