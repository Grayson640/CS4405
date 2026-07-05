package com.example.unit3

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room3.ColumnInfo
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Delete
import androidx.room3.Entity
import androidx.room3.Insert
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.room3.Update
import com.example.unit3.ui.theme.Unit3Theme
import kotlinx.coroutines.flow.Flow
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
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
