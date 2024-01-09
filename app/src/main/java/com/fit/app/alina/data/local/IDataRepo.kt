package com.fit.app.alina.data.local

import com.fit.app.alina.data.User

interface IDataRepo {
    suspend fun insertUser(user: User)

    suspend fun getUser(): User

    suspend fun deleteAll()
}