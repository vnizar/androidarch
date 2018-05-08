package voen.example.androidarch.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

/**
 * Created by voen on 01/05/18.
 */

@Entity(foreignKeys = [(ForeignKey(entity = User::class, parentColumns = arrayOf("id"), childColumns = arrayOf("userId"), onDelete = CASCADE))])
data class Repo(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "repo_name")
        val name: String,
        val url: String,
        val userId: String
) : ItemList()