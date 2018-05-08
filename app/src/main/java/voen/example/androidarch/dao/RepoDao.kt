package voen.example.androidarch.dao

import android.arch.persistence.room.*
import voen.example.androidarch.entity.Repo

/**
 * Created by voen on 01/05/18.
 */

@Dao
interface RepoDao {
    @Insert
    fun insert(repo: Repo)

    @Update
    fun update(vararg repos: Repo)

    @Delete
    fun delete(vararg repos: Repo)

    @Query("SELECT * FROM repo")
    fun getAllRepos(): List<Repo>

    @Query("SELECT * FROM repo INNER JOIN user ON repo.userId = user.id")
    fun getAllReposAndUsers(): List<Repo>

    @Query("SELECT * FROM repo WHERE id = :id")
    fun findRepoById(id: String): Repo

    @Query("SELECT * FROM repo WHERE userId = :userId")
    fun findRepoByUserId(userId: String): List<Repo>
}