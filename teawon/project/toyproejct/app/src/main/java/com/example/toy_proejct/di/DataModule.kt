package com.example.toy_proejct.di

import com.example.toy_proejct.domain.product.ProductRepository
import com.example.toy_proejct.domain.product.ProductRepositoryImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

object DataModule {
    private val client: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    val productRepository: ProductRepository = ProductRepositoryImpl(client)

 /**  [의존성 주입(DI)과 이유]
  *    1. client는 모든 api에서 사용된다. 따라서 매 호출마다 각각 부르는 것보다 하나의 모듈로 관리해야 한다. 보일러플레이트 문제의 방지
  *
  *    2. 인터페이스를 통한 도메인 구현
  *    23번째 줄을 보면 인터페이스로 선언한 ProductRepository을 통해 실제 필요한 객체를 불러오고있다.
  *
  *    3. 각 인터페이스를 상속받아 대해 실제 데이터을 가져오는 도메인을 각 DB , 특성에 따라 구현
  *
  *    4. 각 ViewModel에서는 위의 도메인객체를 통해 값을 가져온다.
  *
  *    -> 왜? 만약 DB를 바꾸게된다면 각각의 종속적인 DB에 대해 접근하는 메소드를 따로 다 만들어야한다.
  *    -> 하지만 23번째 줄에서 이미 구현한 도메인에 대해 사용하려는 도메인만 바꿔주면 전체 코드를 뜯어고치지 않고 바로 변경이 가능하다.
  *
  *
  * **/
}