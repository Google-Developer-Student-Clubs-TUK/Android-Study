package com.example.toy_proejct.scenarios.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.toy_proejct.ui.component.CommonComponent


//private val productList = listOf("A", "B", "C")

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Search(viewModel, { ItemContent() })
    Column(modifier = Modifier.fillMaxWidth()) {
        //화면에 보여지는 부분을 구성
//        productList.forEach{ item ->
//            ProductItem(item)
//        }

        Spacer(modifier = Modifier.weight(1f))
        CommonComponent.ButtomNavbar()
    }

}

//@Composable
//fun Body(content: @Composable () -> Unit) {
//    ItemTheme {
//        val scope = rememberCoroutineScope()
//        val scaffoldState = rememberScaffoldState()
//
//        Scaffold(topBar = {
//            TopAppBar(backgroundColor = Color.Gray, elevation = 5.dp,
//                title = { Text(text = "Items") },
//                navigationIcon = {
//                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                },
//                actions = {
//                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
//                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
//                }
//            )
//        }
//        ) {
//        content()
//    }
//    }
//}


@Composable
fun ItemContent(itemList: List<String> = listOf(
    "맥북",
    "맥북2",
    "아이폰14",
    "아이폰프로")){
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn{
            items(items = itemList){
                //Text(text = it)
                ItemRow(item = it)
            }
        }
    }
}

@Composable
fun ItemRow(item: String){ //각 상품에 대한 설명
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .height(120.dp),
    shape = RoundedCornerShape(corner = CornerSize(14.dp)),
        elevation = 5.dp) {
        Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = RectangleShape,
                elevation = 4.dp){
                Icon(imageVector = Icons.Default.AccountBox,
                contentDescription = "Item Image")
            }
            Text(text=item)
            }
        }
    }




@Composable
private fun ProductItem(item: String) {
    Column {
        Text(text = item)
    }
}

@Composable
private fun Search(viewModel: HomeViewModel, content: @Composable () -> Unit) {
    val searchWidgetState by viewModel.searchWidgetState //활성화 여부
    val searchTextState by viewModel.searchTextState // 검색 변수

    Scaffold(
        topBar = {
            SearchBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it) //텍스트 값이 바뀌면 해당 텍스트로 변수 업데이트
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newState = false) // 버튼이 눌리면 Search영역 비활성화

                },
                onSearchClicked = {
                    Log.d("Searched Text", it) //검색버튼이 눌리면 특정 함수 실행
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newState = true) //Search영역이 클릭되면 Search영역 활성화
                    viewModel.updateSearchTextState("")
                }
            )
        }
    ) {
        content()
    }
}


@Composable
fun SearchBar(
    searchWidgetState: Boolean,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
       false -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered, //영역이 비활성화라면 초기에 보여줄 컴포넌트로 보여주기
                text = searchTextState
            )
        }
        true -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit , text: String) {
    TopAppBar(
        title = {
            if(text.isNotEmpty()){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    textAlign = TextAlign.Center
                )
            }
            else{
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text="상품 검색",
                    textAlign = TextAlign.Center

                )
            }
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() } //버튼 클릭시 Search영역 활성화
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "검색하기...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                            onCloseClicked() //취소 버튼 누르면 closeClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    onCloseClicked()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}


//@Composable
//@Preview
//fun DefaultAppBarPreview() {
//    DefaultAppBar(onSearchClicked = {})
//}
//
//@Composable
//@Preview
//fun SearchAppBarPreview() {
//    SearchAppBar(
//        text = "Some random text",
//        onTextChange = {},
//        onCloseClicked = {},
//        onSearchClicked = {}
//    )
//}