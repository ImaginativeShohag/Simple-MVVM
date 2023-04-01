package org.imaginativeworld.simplemvvm.db

import androidx.room.*
import org.imaginativeworld.simplemvvm.models.DemoUserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userModel: DemoUserEntity): Long

    @Update
    suspend fun updateUser(userModel: DemoUserEntity)

    @Delete
    suspend fun deleteUser(userModel: DemoUserEntity)

    @Query("DELETE FROM users")
    suspend fun removeAllUsers()

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<DemoUserEntity>
}
