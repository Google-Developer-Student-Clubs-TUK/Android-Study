package com.example.toy_proejct.scenarios.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import kotlinx.coroutines.CoroutineScope


class HomeActivity : ComponentActivity()  {
       val viewModel by viewModels<HomeViewModel>() //암기
//
//    private lateinit var job: Job
//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job
//
//    lateinit var tvResult: TextView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        job.cancel() // Activity종료시 job이 종료되도록 한다.
//    }
//
//    fun onSimpleRequest(view: View) {
//        launch(Dispatchers.Main) {
//            // 앞에서 만들었던 코드를 호출하고 결과를 받아와서 화면에 출력해 준다.
//            val result = HttpRequestHelper().requestKtorIo()
//            tvResult.text = result
//        }
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // job = Job() // CoroutineScope를 위해 job을 할당
//        setContentView(R.layout.activity_main)
//        tvResult = findViewById(R.id.tv_result_view)
        setContent {
            HomeScreen(viewModel)
        }
    }
}

