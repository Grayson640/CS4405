package com.example.unit3.network

import com.example.unit3.Issue

data class RemoteIssue(
    val id: String? = null,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val creationDate: String
)

fun Issue.toRemote(): RemoteIssue =
    RemoteIssue(
        id = remoteId,
        title = title,
        description = description,
        priority = priority,
        status = status,
        creationDate = creationDate
    )

fun RemoteIssue.toLocal(): Issue =
    Issue(
        remoteId = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        creationDate = creationDate
    )