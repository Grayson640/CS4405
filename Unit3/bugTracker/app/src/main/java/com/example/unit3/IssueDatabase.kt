package com.example.unit3

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities=[(Issue::class)], version = 2, exportSchema = false)
abstract class IssueDatabase: RoomDatabase() {
    abstract fun issueDao(): IssueDao

    companion object {
        @Volatile
        private var INSTANCE: IssueDatabase? = null

        internal fun getDatabase(context: Context): IssueDatabase {
            if (INSTANCE == null) {
                synchronized(IssueDatabase::class.java){
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            IssueDatabase::class.java,
                            "issue_database")
                            .fallbackToDestructiveMigration(true)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

    }

}