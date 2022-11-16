package com.example.gdsc_androidstudy.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gdsc_androidstudy.R
import com.example.gdsc_androidstudy.data.PostResponse
import com.example.gdsc_androidstudy.main.post.PostViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(userId: String, viewModel: PostViewModel = viewModel(), navHostController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getUserProfile(userId)
    }
    val user = viewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = user.value!!.nickname) },
                actions = {
                    Row() {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        }
    ) {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            if (user.value != null) {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentScale = ContentScale.Crop,
                                contentDescription = "userImage",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(
                                        100.dp
                                    )
                                    .aspectRatio(1f)
                            )
                        }
                        Row(modifier = Modifier.weight(2f)) {
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "20", fontWeight = FontWeight.SemiBold)
                                Text(text = "게시물")
                            }
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "205", fontWeight = FontWeight.SemiBold)
                                Text(text = "팔로워")
                            }
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "129", fontWeight = FontWeight.SemiBold)
                                Text(text = "팔로잉")
                            }
                        }
                    }
                    Row(modifier = Modifier.padding(vertical = 10.dp)) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.weight(1f).height(30.dp),
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        ) {
                            Text(text = "프로필 편집", fontSize = 11.sp)
                        }
                        Spacer(modifier = Modifier.width(7.dp))
                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(30.dp)) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            //
                        }
                    }
                }
                Tab(viewModel, navHostController)
            }else{
                Text("데이터 불러오기 실패")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tab(postViewModel: PostViewModel, navHostController: NavHostController) {
    val tabList = mapOf(
        "게시글" to Icons.Filled.List,
        "숏츠" to Icons.Filled.AccountBox,
        "태그" to Icons.Filled.Face
    )

    val pagerState = rememberPagerState(pageCount = 7)
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier.height(40.dp),
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        tabList.keys.forEachIndexed { index, title ->
            Tab(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                selected = pagerState.currentPage == index,
                content = {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Icon(
                                imageVector = tabList[title]!!,
                                contentDescription = title,
                                modifier = Modifier.size(23.dp),
                                tint = Color.Black
                            )
                        }
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.DarkGray
            )
        }
    }
    HorizontalPager(state = pagerState) { page ->
        MyPostList(photos = postViewModel.postData.collectAsState().value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPostList(photos: List<PostResponse>?) {
    photos?.let {
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
            items(photos) { photo ->
                Image(painter = painterResource(id = R.drawable.post), contentDescription = "contentImage", modifier = Modifier.fillMaxWidth().aspectRatio(1f), contentScale = ContentScale.Crop)
            }
        }
    }
}
