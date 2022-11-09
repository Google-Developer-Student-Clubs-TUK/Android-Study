package com.example.gdsc_androidstudy.main // ktlint-disable package-name

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gdsc_androidstudy.R
import com.example.gdsc_androidstudy.data.CommentData
import com.example.gdsc_androidstudy.main.post.PostViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CommentScreen(postId: Int, viewModel: PostViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navHostController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("댓글", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "menu", modifier = Modifier.rotate(90f))
                }
            },
            navigationIcon = {
                IconButton(onClick = navHostController::navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(3) {
                    CommentItem()
                }
            }
            SendComment(viewModel = viewModel, postId = postId)
        }
    }
}

@Composable
fun CommentItem() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentScale = ContentScale.Crop,
            contentDescription = "userImage",
            modifier = Modifier
                .clip(CircleShape)
                .size(
                    38.dp
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 23.sp)) {
                        withStyle(style = SpanStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)) {
                            append("zinkikixx ")
                        }
                        withStyle(style = SpanStyle(fontSize = 11.sp)) {
                            append("안뇽안뇽어ㄷ쩌구~~~~~~~~~~헬로안뇽안뇽어ㄷ쩌구안뇽안뇽어ㄷ쩌구안뇽안뇽어ㄷ쩌구~")
                        }
                    }
                }
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.width(30.dp)) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "", modifier = Modifier.size(13.dp))
        }
    }
}

@Composable
fun SendComment(viewModel: PostViewModel, postId: Int, modifier: Modifier = Modifier) {
    var sendMessage by remember {
        mutableStateOf("")
    }
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp, start = 15.dp, end = 15.dp).height(40.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentScale = ContentScale.Crop,
            contentDescription = "userImage",
            modifier = Modifier
                .clip(CircleShape)
                .size(
                    30.dp
                )
        )
        OutlinedTextField(
            value = sendMessage,
            onValueChange = { sendMessage = it },
            modifier = Modifier
                .weight(1f)
                .padding(10.dp).height(47.dp),
            shape = RoundedCornerShape(30.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    // 메세지 보내기
                    if (sendMessage.isNotEmpty()) {
                        val now = System.currentTimeMillis()
                        val message = CommentData(postId, sendMessage, userId!!, now)
                        sendMessage = ""
                    }
                }
            )
        )
        IconButton(
            onClick = {
                // 메세지 보내기
                if (sendMessage.isNotEmpty()) {
                    val now = System.currentTimeMillis()
                    val message = CommentData(postId, sendMessage, userId!!, now)
                    sendMessage = ""
                }
            },
            modifier = Modifier.size(25.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "보내기",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
