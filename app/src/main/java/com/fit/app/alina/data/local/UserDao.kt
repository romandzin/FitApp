package com.fit.app.alina.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fit.app.alina.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUser(): User

    @Insert
    suspend fun insertUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll();
}