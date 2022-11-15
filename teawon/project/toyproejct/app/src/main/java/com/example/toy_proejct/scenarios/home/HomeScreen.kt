package com.example.toy_proejct.scenarios.home

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.toy_proejct.LogHelper
import com.example.toy_proejct.data.product.list.ProductListDto
import com.example.toy_proejct.scenarios.detail.DetailActivity
import com.example.toy_proejct.ui.component.CommonComponent
import com.example.toy_proejct.utils.UnitHelper
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content


@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val scrollState = rememberLazyListState()
    val scrollUpState = viewModel.scrollUp.observeAsState()
    viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)

    Search(viewModel,scrollUpState) {
        Column(modifier = Modifier.fillMaxSize()) {
            ItemContent(modifier = Modifier.weight(1f), itemList = viewModel.itemList.value, scrollState=scrollState)
            CommonComponent.ButtomNavbar()
        }
    }
}


@Composable
fun ItemContent(modifier: Modifier = Modifier, itemList: List<ProductListDto>, scrollState:LazyListState) {
    Column(
        modifier = modifier
            .padding(12.dp)
    ) {
        LazyColumn(state = scrollState) {
            items(items = itemList) {
                ItemRow(item = it)
            }
        }
    }
}

@Composable
fun ItemRow(item: ProductListDto) { //각 상품에 대한 설명
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(320.dp)
            .clickable {
                context.startActivity(
                    Intent(context, DetailActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra("url", item.url)
                    }
                )
            },
        shape = RoundedCornerShape(corner = CornerSize(14.dp)),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = item.imageUrl,
                    contentDescription = "image"
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = UnitHelper.getStringFromMoneyInteger(item.minimumPrice),
                    style = MaterialTheme.typography.h6
                )
            }


        }


    }


}


@Composable
private fun Search(viewModel: HomeViewModel, scrollUpState:
State<Boolean?>, content: @Composable () -> Unit ) {
    val searchWidgetState by viewModel.searchWidgetState //활성화 여부
    val searchTextState by viewModel.searchTextState // 검색 변수
    val isLoading by viewModel.isLoading //로딩 함수


    val coroutineScope = rememberCoroutineScope() //코루틴 생성





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
                    coroutineScope.launch { viewModel.searchApi(it) } //코루틴에서 Ktor-api호출
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newState = true) //Search영역이 클릭되면 Search영역 활성화
                    viewModel.updateSearchTextState("")
                },
                scrollUpState = scrollUpState
            )
        }
    ) {
        if (isLoading) {
            CommonComponent.LoadingSpinner()
        } else {
            content()
        }
    }
}



@Composable
fun SearchBar(
    searchWidgetState: Boolean,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    scrollUpState : State<Boolean?>

    ) {

    when (searchWidgetState) {
        false -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered, //영역이 비활성화라면 초기에 보여줄 컴포넌트로 보여주기
                text = searchTextState,
                scrollUpState = scrollUpState
            )
        }
        true -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked,
                scrollUpState = scrollUpState
            )
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit, text: String, scrollUpState: State<Boolean?>) {
    val position by animateFloatAsState(if (scrollUpState.value == true) -150f else 0f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .graphicsLayer { translationY = (position) },
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TopAppBar(
            title = {
                if (text.isNotEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "상품 검색",
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
            },
            navigationIcon = {
                Spacer(modifier = Modifier.size(20.dp))
            }
        )
    }
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    scrollUpState: State<Boolean?>,
) {

    val position by animateFloatAsState(if (scrollUpState.value == true) -150f else 0f)
    LogHelper.print("자고싶어요: $scrollUpState")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .graphicsLayer { translationY = (position) },
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






