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

class AddIssueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_issue)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val priorityLabel = findViewById<TextView>(R.id.priorityLevelTextView)
        val statusLabel = findViewById<TextView>(R.id.statusLevelTextView)
        val prioritySwitch = findViewById<Switch>(R.id.prioritySwitch)
        val statusSwitch = findViewById<Switch>(R.id.statusSwitch)

        val repository = IssueRepository(application)

        prioritySwitch.isChecked = false
        priorityLabel.text = "Low"

        statusSwitch.isChecked = false
        statusLabel.text = "Closed"

        cancelButton.setOnClickListener {
            runOnUiThread {
                Toast.makeText(this@AddIssueActivity, "Cancelled", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AddIssueActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

                finish()
            }
        }

        prioritySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                priorityLabel.text = "High"
            } else {
                priorityLabel.text = "Low"
            }
        }
        statusSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                statusLabel.text = "Open"
            } else {
                statusLabel.text = "Closed"
            }
        }

        saveButton.setOnClickListener {
            val title = findViewById<EditText>(R.id.editTitleText).text.toString()
            val description = findViewById<EditText>(R.id.editDescriptionText).text.toString()

            if (title.isBlank() || description.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!statusSwitch.isChecked) {
                statusSwitch.isChecked = true
                statusLabel.text = "Open"
            }

            val priority = if (prioritySwitch.isChecked) "High" else "Low"
            val status = if (statusSwitch.isChecked) "Open" else "Closed"

            lifecycleScope.launch {

                val newIssue = Issue(
                    title = title,
                    description = description,
                    priority = priority,
                    status = status,
                    creationDate = System.currentTimeMillis().toString()
                )
                repository.insert(newIssue)
            }

            runOnUiThread {
                Toast.makeText(this@AddIssueActivity, "Issue added", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AddIssueActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

                finish()
            }
        }
    }
}