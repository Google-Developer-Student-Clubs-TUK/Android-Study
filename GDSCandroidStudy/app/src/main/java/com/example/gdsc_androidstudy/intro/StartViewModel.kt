package com.example.gdsc_androidstudy.intro // ktlint-disable package-name

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gdsc_androidstudy.data.User
import com.example.gdsc_androidstudy.data.UserRequest
import com.example.gdsc_androidstudy.network.DBRepository
import com.example.gdsc_androidstudy.utilites.App
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartViewModel : ViewModel() {
    sealed class LoginState {
        object Loading : LoginState()
        object RequireGoogleLogin : LoginState()
        object RequireRegister : LoginState()
        object CompletedLogin : LoginState()
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState = _loginState.asStateFlow()

    private val hasId = MutableStateFlow(false)
    private val dbRepository = DBRepository()

    init {
        viewModelScope.launch {
            checkLogin(
                kotlin.runCatching {
                    listOf(
                        async {
                            getLastSignedInAccount(App.context)
                        },
                        async {
                            delay(2000)
                        }
                    ).awaitAll()[0] as GoogleSignInAccount?
                }.getOrNull() != null
            )
        }
    }

    fun checkLogin(isLogin: Boolean) {
        // sharedPreference에 저장된 데이터가 있으면 가입된 유저라고 간주
        viewModelScope.launch {
            if (isLogin) {
                hasId.value = App.appPref.getUserPref() == null
                if (hasId.value) {
                    _loginState.emit(LoginState.CompletedLogin)
                } else {
                    hasAccount()?.let { user ->
                        App.appPref.setUserPref(user)
                        _loginState.emit(LoginState.CompletedLogin)
                    } ?: kotlin.run {
                        _loginState.emit(LoginState.RequireRegister)
                    }
                }
            } else {
                _loginState.emit(LoginState.RequireGoogleLogin)
            }
        }
    }

    fun checkNickname(nickname: String): Boolean {
        // 닉네임 중복 아니면 true
        return true
    }

    private suspend fun hasAccount(): User? {
        return kotlin.runCatching {
            dbRepository.getProfile(auth.currentUser!!.uid)
        }.getOrNull()
    }

    fun joinAccount(nickname: String) {
        auth.currentUser?.let {
            val userReq = UserRequest(it.uid, nickname, email = it.email!!, profileImg = null)
            viewModelScope.launch {
                kotlin.runCatching {
                    dbRepository.joinUser(userReq)
                }.onSuccess { user ->
                    App.appPref.setUserPref(user)
                    _loginState.emit(LoginState.CompletedLogin)
                }.onFailure {
                    Log.d("zinkiki", it.message.toString())
                }
            }
        }
    }

    // 이전에 로그인 한 계정이 있는지 확인
    private fun getLastSignedInAccount(context: Context) =
        GoogleSignIn.getLastSignedInAccount(context)
}
