package com.example.toy_proejct.scenarios.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

import android.content.Intent
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.toy_proejct.api.getDetail.DetailDto
import com.example.toy_proejct.api.getDetail.MallDtoInfo
import com.example.toy_proejct.api.getSearchList.ProductListDto
import com.example.toy_proejct.scenarios.detail.DetailActivity
import com.example.toy_proejct.scenarios.home.ItemRow
import com.example.toy_proejct.ui.component.CommonComponent
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(viewModel: DetailViewModel, title2: String?, minimumPrice2: Int, back:() -> Unit) {

    val title = title2
    val minimumPrice = minimumPrice2
    val coroutineScope = rememberCoroutineScope() //코루틴 생성

    LaunchedEffect(true){
        coroutineScope.launch{viewModel.getDetailInfo()}
    }

    DetailComponent(viewModel,back, title, minimumPrice, itemList = viewModel.detailInfo.value)
}

@Composable
private fun DetailComponent(
    viewModel:DetailViewModel,
    back: () -> Unit,
    title: String?,
    minimumPrice: Int,
    itemList: DetailDto,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    back()
                })
            Text(
                text = title!!, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
        }
        Text(text = "가격: ${minimumPrice}원")
        LazyColumn{
            items(items = itemList.mallDtoInfo){
                MallList(item = it)
            }
        }
    }
}

@Composable
fun MallList(item: MallDtoInfo) { //각 상품에 대한 설명
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = item.link,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = item.link,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = item.paymentOption,
                    style = MaterialTheme.typography.h6
                )
            }

        }
    }
}