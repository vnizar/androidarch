@file:JvmName("User")
package voen.example.androidarch.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by voen on 10/01/18.
 */

@Entity(tableName = "user")
data class User(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "first_name")
        val firstName: String,
        @ColumnInfo(name = "last_name")
        val lastName: String
) : ItemList()