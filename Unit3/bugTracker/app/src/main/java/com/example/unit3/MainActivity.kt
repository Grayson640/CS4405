package com.example.unit3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val addButton = findViewById<Button>(R.id.addIssueButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddIssueActivity::class.java)
            startActivity(intent)
        }

        val issue1Button = findViewById<TextView>(R.id.issueTextView1)
        issue1Button.setOnClickListener {
            val intent = Intent(this, EditIssueActivity::class.java)
            startActivity(intent)
        }

        val issue2Button = findViewById<TextView>(R.id.issueTextView2)
        issue2Button.setOnClickListener {
            val intent = Intent(this, EditIssueActivity::class.java)
            startActivity(intent)
        }

        val issue3Button = findViewById<TextView>(R.id.issueTextView3)
        issue3Button.setOnClickListener {
            val intent = Intent(this, EditIssueActivity::class.java)
            startActivity(intent)
        }
    }
}
