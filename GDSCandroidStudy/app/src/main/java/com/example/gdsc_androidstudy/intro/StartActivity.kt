package com.example.gdsc_androidstudy.intro

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.gdsc_androidstudy.MainActivity
import com.example.gdsc_androidstudy.R
import com.example.gdsc_androidstudy.view.JoinDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StartActivity : ComponentActivity() {

    private val viewModel by viewModels<StartViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1313

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                var joinDialogState by remember {
                    mutableStateOf(false)
                }
                if (joinDialogState) {
                    JoinDialog(changeDialogState = { joinDialogState = it }, viewModel = viewModel)
                }
                when (viewModel.loginState.collectAsState().value) {
                    StartViewModel.LoginState.RequireLogin -> {
                        SignInGoogleButton { googleLoin() }
                    }
                    StartViewModel.LoginState.RequireJoin -> {
                        joinDialogState = true
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.loginState.collect {
                    when (it) {
                        StartViewModel.LoginState.CompletedLogin -> toMainActivity()
                    }
                }
            }
        }
    }

    private fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    // 로그인 객체 생성
    private fun googleLoin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // 빨간줄이지만 토큰 문제라 실행 가능
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignIn()
    }

    // 구글 회원가입
    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "구글 회원가입에 실패하였습니다: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            /*no-op*/
        }
    }

    // account 객체에서 id 토큰 가져온 후 Firebase 인증
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        val auth = FirebaseAuth.getInstance()
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    viewModel.checkLogin(true)
                }
            }
        }
    }
}

@Composable
fun SignInGoogleButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
        color = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.small,
        elevation = 5.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(
                start = 14.dp,
                end = 12.dp,
                top = 11.dp,
                bottom = 11.dp
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Google sign button",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Sign in with Google",
                style = MaterialTheme.typography.overline,
                color = Color.Gray,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
