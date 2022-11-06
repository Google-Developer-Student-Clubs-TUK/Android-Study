package com.example.toy_proejct.scenarios.home.data

data class Item(
    val url : String,
    val title : String,
    val price : Int,
    val image_url : String
)

fun getTempItems(): List<Item> {
    return listOf(
        Item(image_url = "http://img.danawa.com/prod_img/500000/674/462/img/15462674_1.jpg?shrink=130:130&_v=20220215194029",
            title = "APPLE 2021 맥북프로16 MK193KH/A",
            price = 2886040,
            url = "https://prod.danawa.com/info/?pcode=15462674&keyword=%EB%A7%A5%EB%B6%81&cate=112758"

    ),
        Item(image_url = "http://img.danawa.com/prod_img/500000/674/462/img/15462674_1.jpg?shrink=130:130&_v=20220215194029",
            title = "APPLE 2021 맥북프로16 MK193KH/A",
            price = 2886040,
            url = "https://prod.danawa.com/info/?pcode=15462674&keyword=%EB%A7%A5%EB%B6%81&cate=112758"

        ),
        Item(image_url = "http://img.danawa.com/prod_img/500000/674/462/img/15462674_1.jpg?shrink=130:130&_v=20220215194029",
            title = "APPLE 2021 맥북프로16 MK193KH/A",
            price = 2886040,
            url = "https://prod.danawa.com/info/?pcode=15462674&keyword=%EB%A7%A5%EB%B6%81&cate=112758"

        ),
        Item(image_url = "http://img.danawa.com/prod_img/500000/674/462/img/15462674_1.jpg?shrink=130:130&_v=20220215194029",
            title = "APPLE 2021 맥북프로16 MK193KH/A",
            price = 2886040,
            url = "https://prod.danawa.com/info/?pcode=15462674&keyword=%EB%A7%A5%EB%B6%81&cate=112758"

        ),
        Item(image_url = "http://img.danawa.com/prod_img/500000/674/462/img/15462674_1.jpg?shrink=130:130&_v=20220215194029",
            title = "APPLE 2021 맥북프로16 MK193KH/A",
            price = 2886040,
            url = "https://prod.danawa.com/info/?pcode=15462674&keyword=%EB%A7%A5%EB%B6%81&cate=112758"

        )
    )
}

