package com.example.unit3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

private var isInitializing = true
private var currentIssue: Issue? = null
class EditIssueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_issue)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Import data selected from home screen
        val issueID = intent.getIntExtra("issue_id", -1)

        // Database setup
        val db = IssueDatabase.getDatabase(this)
        val dao = db.issueDao()

        lifecycleScope.launch {
            val issue = dao.findID(issueID)

            if (issue != null){
                populateUI(issue)
            }
        }

        val deleteButton = findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            currentIssue?.let { issue ->
                lifecycleScope.launch {
                    val db = IssueDatabase.getDatabase(this@EditIssueActivity)
                    val dao = db.issueDao()

                    val repository = IssueRepository(application)

                    repository.delete(issue)

                    runOnUiThread {
                        Toast.makeText(this@EditIssueActivity,
                            "Issue deleted", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@EditIssueActivity,
                            MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                        finish()
                    }
                }
            }
        }

        // UI Setup
        val prioritySwitch = findViewById<Switch>(R.id.prioritySwitch)
        val priorityLevelText = findViewById<TextView>(R.id.priorityLevelTextView)

        val statusSwitch = findViewById<Switch>(R.id.statusSwitch)
        val statusLevelText = findViewById<TextView>(R.id.statusLevelTextView)

        val saveButton = findViewById<Button>(R.id.updateButton)

        prioritySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isInitializing) return@setOnCheckedChangeListener

            if (isChecked){
                priorityLevelText.text = "High"
            } else {
                priorityLevelText.text = "Low"
            }
        }

        statusSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isInitializing) return@setOnCheckedChangeListener

            if (isChecked) {
                statusLevelText.text = "Open"
            } else {
                statusLevelText.text = "Closed"
            }
        }

        // saveButton Logic
        saveButton.setOnClickListener {

            val title = findViewById<EditText>(R.id.editTitleText).text.toString()
            val description = findViewById<EditText>(R.id.editDescriptionText).text.toString()

            if (title.isBlank() || description.isBlank()) {
                Toast.makeText(this, "Please fill in all fields",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedIssue = currentIssue?.copy(
                title = title,
                description = description,
                priority = if (prioritySwitch.isChecked) "High" else "Low",
                status = if (statusSwitch.isChecked) "Open" else "Closed"
            )

            if (updatedIssue != null) {
                lifecycleScope.launch {
                    val db = IssueDatabase.getDatabase(this@EditIssueActivity)
                    val dao = db.issueDao()

                    val repository = IssueRepository(application)

                    repository.update(updatedIssue)

                    runOnUiThread {
                        Toast.makeText(this@EditIssueActivity,
                            "Issue updated", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@EditIssueActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                        finish()
                    }
                }
            }
        }
    }

    private fun populateUI(issue: Issue) {

        isInitializing = true
        currentIssue = issue

        findViewById<EditText>(R.id.editTitleText).setText(issue.title)
        findViewById<EditText>(R.id.editDescriptionText).setText(issue.description)

        val prioritySwitch = findViewById<Switch>(R.id.prioritySwitch)
        val statusSwitch = findViewById<Switch>(R.id.statusSwitch)

        val priorityText = findViewById<TextView>(R.id.priorityLevelTextView)
        val statusText = findViewById<TextView>(R.id.statusLevelTextView)

        if (issue.priority == "High") {
            prioritySwitch.isChecked = true
            priorityText.text = "High"
        } else {
            prioritySwitch.isChecked = false
            priorityText.text = "Low"
        }

        if (issue.status == "Open") {
            statusSwitch.isChecked = true
            statusText.text = "Open"
        } else {
            statusSwitch.isChecked = false
            statusText.text = "Closed"
        }

        isInitializing = false
    }
}

