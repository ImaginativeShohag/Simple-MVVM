package org.imaginativeworld.simplemvvm.db

import androidx.room.*
import org.imaginativeworld.simplemvvm.models.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userModel: UserEntity): Long


    @Update
    suspend fun updateUser(userModel: UserEntity)


    @Delete
    suspend fun deleteUser(userModel: UserEntity)


    @Query("DELETE FROM tbl_user")
    suspend fun removeAllUsers()


    @Query("SELECT * FROM tbl_user")
    suspend fun getUsers(): List<UserEntity>


}