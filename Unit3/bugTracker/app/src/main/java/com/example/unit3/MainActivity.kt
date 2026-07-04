package com.example.unit3

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello bugTracker!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Unit3Theme {
        Greeting("Android")
    }
}

@Entity(tableName = "issues")
data class Issue (
    @PrimaryKey(true)
    @ColumnInfo(name = "issueID")
    val id: Int = 0,

    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val creationDate: String
)

@Dao
interface IssueDao {
    @Query("SELECT * FROM issues")
    fun getAllIssues(): Flow<List<Issue>>

    @Query("SELECT * FROM issues WHERE issueID = :id")
    suspend fun findID(id: Int): Issue?

    @Insert
    suspend fun addIssue(issue: Issue)

    @Query("DELETE FROM issues WHERE issueID = :id")
    suspend fun deleteIssue(id: Int)

    @Delete
    suspend fun deleteIssue(issue: Issue)

    @Update
    suspend fun updateIssue(issue: Issue)
}

@Database(entities=[(Issue::class)], version = 1, exportSchema = false)
abstract class IssueDatabase: RoomDatabase() {
    abstract fun issueDao(): IssueDao

    companion object {
        @Volatile
        private var INSTANCE: IssueDatabase? = null

        internal fun getDatabase(context: Context): IssueDatabase? {
            if (INSTANCE == null) {
                synchronized(IssueDatabase::class.java){
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            IssueDatabase::class.java,
                            "issue_database").build()
                    }
                }
            }
            return INSTANCE
        }

    }

}

class IssueRepository(application: Application) {

    private var issueDao: IssueDao?

    init {
        val db: IssueDatabase?=
            IssueDatabase.getDatabase(application)
        issueDao = db?.issueDao()
    }
}