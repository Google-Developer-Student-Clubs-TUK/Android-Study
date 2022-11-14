package com.example.toy_proejct.domain.user

import com.example.toy_proejct.data.User

interface UserRepository {
    suspend fun getUserList(): List<User>
}