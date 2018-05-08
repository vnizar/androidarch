package voen.example.androidarch.deps.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import voen.example.androidarch.database.AppDatabase
import voen.example.androidarch.database.DatabaseMigration
import javax.inject.Singleton

/**
 * Created by voen on 10/01/18.
 */

@Module
class DatabaseModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesRoomDatabase() = Room
            .databaseBuilder(context, AppDatabase::class.java, "user-database")
            // destroy old db when migrate
//            .fallbackToDestructiveMigration()
            .addMigrations(DatabaseMigration.FROM_3_TO_4)
            .build()

    @Singleton
    @Provides
    fun providesUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

    @Singleton
    @Provides
    fun providesRepoDao(appDatabase: AppDatabase) = appDatabase.repoDao()
}