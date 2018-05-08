@file:JvmName("UserDao")
package voen.example.androidarch.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import voen.example.androidarch.entity.User

/**
 * Created by voen on 10/01/18.
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT COUNT(*) FROM user")
    fun countUser() : Int

    @Query("SELECT * FROM user WHERE first_name LIKE :firstName")
    fun findByFirstName(firstName: String): User

    @Query("SELECT * FROM user WHERE last_name LIKE :lastName")
    fun findByLastName(lastName: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun deleteUser(user: User)
}