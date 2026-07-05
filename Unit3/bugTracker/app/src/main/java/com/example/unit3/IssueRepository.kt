package com.example.unit3

import android.app.Application

class IssueRepository(application: Application) {

    private var issueDao: IssueDao?

    init {
        val db: IssueDatabase?=
            IssueDatabase.getDatabase(application)
        issueDao = db?.issueDao()
    }
}