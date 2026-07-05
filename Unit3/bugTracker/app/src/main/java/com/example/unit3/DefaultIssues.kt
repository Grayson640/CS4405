package com.example.unit3

object DefaultIssues {
    val issues = listOf(
        Issue(
            title = "Printer not responding",
            description = "Printer software appears to be working, but does not print.",
            priority = "High",
            status = "Open",
            creationDate = "2026-07-05"
        ),
        Issue(
            title = "Cannot edit saved word files",
            description = "After printing, word file does not respond unless restarted.",
            priority = "Low",
            status = "Open",
            creationDate = "2026-07-04"
        ),
        Issue(
            title = "Excel freezes",
            description = "Excel freezes during power automate operations",
            priority = "High",
            status = "Open",
            creationDate = "2026-07-05"
        )
    )
}
