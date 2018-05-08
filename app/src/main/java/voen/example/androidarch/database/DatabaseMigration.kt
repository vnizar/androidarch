package voen.example.androidarch.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

/**
 * Created by voen on 01/05/18.
 */
class DatabaseMigration {
    companion object {
        val FROM_3_TO_4 = object: Migration(3, 4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user RENAME TO _user_old")
                database.execSQL("CREATE TABLE user (id TEXT NOT NULL PRIMARY KEY, first_name TEXT NOT NULL, last_name TEXT NOT NULL)")
                database.execSQL("INSERT INTO user (id, first_name, last_name) SELECT id, first_name, last_name FROM _user_old")
            }
        }
    }
}