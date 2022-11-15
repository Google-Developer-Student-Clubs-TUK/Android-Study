package com.example.gdsc_androidstudy.main.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gdsc_androidstudy.R
import com.example.gdsc_androidstudy.data.PostResponse

@Composable
fun PostPage(navHostController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val viewModel = remember {
        PostViewModel()
    }
    buildAnnotatedString { }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.image),
                            contentDescription = ""
                        )
                    }
                },
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
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "",
                                tint = Color.Black,
                                modifier = Modifier.rotate(315f).padding(bottom = 4.dp)
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
        Surface(modifier = Modifier.padding(it)) {
            PostList(posts = viewModel.postData.collectAsState(), navHostController)
        }
    }
}

@Composable
fun PostList(posts: State<List<PostResponse>?>, navHostController: NavHostController) {
    val scope = LazyListState()
    posts.value?.let {
        LazyColumn(state = scope, modifier = Modifier.padding(bottom = 45.dp)) {
            items(it) { item ->
                PostItem(postData = item, navHostController = navHostController)
            }
        }
    }
}
