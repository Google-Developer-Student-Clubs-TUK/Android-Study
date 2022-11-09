package com.example.gdsc_androidstudy.main // ktlint-disable package-name

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gdsc_androidstudy.R

@Composable
fun CommentScreen(postId: Int, navHostController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("댓글", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = navHostController::navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }) {
        CommentItem()
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
                        withStyle(style = SpanStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)) {
                            append("zinkikixx ")
                        }
                        withStyle(style = SpanStyle(fontSize = 15.sp)) {
                            append("안뇽안뇽어ㄷ쩌구~~~~~~~~~~헬로안뇽안뇽어ㄷ쩌구안뇽안뇽어ㄷ쩌구안뇽안뇽어ㄷ쩌구~")
                        }
                    }
                }
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(20.dp)) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "", modifier = Modifier.size(17.dp))
        }
    }
}
