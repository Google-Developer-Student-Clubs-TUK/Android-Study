package com.example.gdsc_androidstudy.main.post // ktlint-disable package-name

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.gdsc_androidstudy.R

@Composable
fun PostItem(postData: PostData) {
    val scrollState = rememberScrollState()
    Column() {
        DrawLine()
        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(13.dp)) {
            val (profile, menu) = createRefs()
            Row(
                modifier = Modifier.constrainAs(profile) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "zinkikixx", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
            IconButton(
                onClick = { },
                modifier = Modifier.size(30.dp).constrainAs(menu) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "menu", modifier = Modifier.rotate(90f))
            }
        }
        Image(painter = painterResource(id = R.drawable.post), contentDescription = "contentImage", modifier = Modifier.fillMaxWidth().aspectRatio(1f), contentScale = ContentScale.Crop)
        Column(modifier = Modifier.padding(15.dp)) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (icons, bookmark) = createRefs()
                Row(
                    modifier = Modifier.constrainAs(icons) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "heart",
                        modifier = Modifier.size(26.dp).clickable { }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Filled.MailOutline,
                        contentDescription = "heart",
                        modifier = Modifier.size(26.dp).clickable { }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "heart",
                        modifier = Modifier.size(26.dp)
                            .clickable { }
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.icon_bookmark),
                    contentDescription = "heart",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp).constrainAs(bookmark) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }.clickable { }
                )
            }
            Text(text = "좋아요 10개", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 10.dp, bottom = 8.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 23.sp)){
                        withStyle(style = SpanStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold)){
                            append("zinkikixx ")
                        }
                        withStyle(style = SpanStyle(fontSize = 15.sp)){
                            append("안뇽안뇽어ㄷ쩌구~~~~~~~~~~헬로안뇽안뇽어ㄷ쩌구안뇽안뇽어ㄷ쩌구안뇽안뇽어ㄷ쩌구~")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "30분 전", fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun DrawLine() {
    val color = colorResource(id = R.color.backgroundGray)
    Canvas(modifier = Modifier.fillMaxWidth()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawLine(
            start = Offset(x = 0f, y = canvasHeight),
            end = Offset(x = canvasWidth, y = canvasHeight),
            color = color,
            strokeWidth = 2.5f
        )
    }
}

@Composable
@Preview
fun ItemPreview() {
    PostItem(postData = PostData("zinkikixx", "", "오늘의 OOTT", listOf(""), ""))
}
