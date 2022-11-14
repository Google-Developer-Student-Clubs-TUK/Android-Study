package com.example.toy_proejct.domain.user

import com.example.toy_proejct.data.User

class UserRepositoryImpl: UserRepository {
    override suspend fun getUserList(): List<User> {
        return emptyList()
    }
}