package com.fit.app.alina.data.local.user

import com.fit.app.alina.data.User

interface IDataUserRepo {
    suspend fun insertUser(user: User)

    suspend fun getUser(): User

    suspend fun deleteAll()
}