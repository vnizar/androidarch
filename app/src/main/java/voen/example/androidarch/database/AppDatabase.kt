@file:JvmName("AppDatabase")
package voen.example.androidarch.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import voen.example.androidarch.dao.RepoDao
import voen.example.androidarch.dao.UserDao
import voen.example.androidarch.entity.Repo
import voen.example.androidarch.entity.User

/**
 * Created by voen on 10/01/18.
 */

@Database(entities = [(User::class), (Repo::class)], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
}