package com.example.toy_proejct.scenarios.home.data

data class Item(
    val url : String,
    val title : String,
    val price : Int,
    val image_url : String
)

fun getTempItems(): List<Item> {
    return listOf(
        Item(image_url ="data:image/png;base64,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"
            ,title = "APPLE 2021 맥북프로16 MK193KH/A",
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

