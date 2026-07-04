package com.example.unit3

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room3.*
import com.example.unit3.ui.theme.Unit3Theme
import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
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