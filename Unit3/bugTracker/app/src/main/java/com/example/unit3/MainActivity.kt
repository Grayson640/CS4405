package com.example.unit3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.unit3.DefaultIssues.issues
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import android.graphics.Color
import android.widget.Toast

private var issueList: List<Issue> = emptyList()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val issue1Button = findViewById<TextView>(R.id.issueTextView1)
        val issue2Button = findViewById<TextView>(R.id.issueTextView2)
        val issue3Button = findViewById<TextView>(R.id.issueTextView3)

        val db = IssueDatabase.getDatabase(applicationContext)!!
        val issueDao = db.issueDao()

        lifecycleScope.launch {
            //issueDao.clearAll()

            val count = issueDao.getIssueCount()

            Toast.makeText(this@MainActivity, "issue count: " +
                    "$count", Toast.LENGTH_LONG).show()

            // Populate database with default values if database is empty
            if (count == 0) {
                issues.forEach {
                    issueDao.addIssue(it)
                }
            }

            issueDao.getAllIssues().collect { list ->
                issueList = list

                if (list.size > 0) {
                    issue1Button.text = list[0].title
                    updateIssueButtons(issue1Button, list[0])
                } else {
                    issue1Button.text = "Empty"
                    issue1Button.setBackgroundColor(Color.GRAY)
                }

                if (list.size > 1) {
                    issue2Button.text = list[1].title
                    updateIssueButtons(issue2Button, list[1])
                } else {
                    issue2Button.text = "Empty"
                    issue2Button.setBackgroundColor(Color.GRAY)
                }

                if (list.size > 2) {
                    issue3Button.text = list[2].title
                    updateIssueButtons(issue3Button, list[2])
                } else {
                    issue3Button.text = "Empty"
                    issue3Button.setBackgroundColor(Color.GRAY)
                }
            }
        }

        val addButton = findViewById<Button>(R.id.addIssueButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddIssueActivity::class.java)
            startActivity(intent)
        }

        issue1Button.setOnClickListener {
            //Toast.makeText(this, "ID = ${issueList[0].id}", Toast.LENGTH_SHORT).show()
            if (issueList.size > 0) {
                val intent = Intent(this, EditIssueActivity::class.java)
                intent.putExtra("issue_id", issueList[0].id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No issue here", Toast.LENGTH_SHORT).show()
            }
        }

        issue2Button.setOnClickListener {
            //Toast.makeText(this, "ID = ${issueList[1].id}", Toast.LENGTH_SHORT).show()
            if (issueList.size > 1) {
                val intent = Intent(this, EditIssueActivity::class.java)
                intent.putExtra("issue_id", issueList[1].id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No issue here", Toast.LENGTH_SHORT).show()
            }
        }

        issue3Button.setOnClickListener {
            //Toast.makeText(this, "ID = ${issueList[2].id}", Toast.LENGTH_SHORT).show()
            if (issueList.size > 2) {
                val intent = Intent(this, EditIssueActivity::class.java)
                intent.putExtra("issue_id", issueList[2].id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No issue here", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Changes color based on priority level and updates title
    fun updateIssueButtons(textView: TextView, issue: Issue){
        textView.text = issue.title
        when (issue.priority) {
            "High" -> textView.setBackgroundColor(Color.RED)
            "Low" -> textView.setBackgroundColor(Color.rgb(255, 165, 0))
            else -> textView.setBackgroundColor(Color.GRAY)
        }
    }
}
