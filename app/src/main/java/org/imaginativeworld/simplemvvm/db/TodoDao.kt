package org.imaginativeworld.simplemvvm.db

import androidx.room.*
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos: List<TodoEntity>)

    @Update
    suspend fun update(userModel: TodoEntity)

    @Delete
    suspend fun delete(userModel: TodoEntity)

    @Query("DELETE FROM todos")
    suspend fun removeAll()

    @Query("SELECT * FROM todos")
    suspend fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TodoEntity?
}
