package com.example.toy_proejct.scenarios.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.toy_proejct.scenarios.detail.data.DetailDto
import com.example.toy_proejct.scenarios.detail.data.MallDtoInfo
import com.example.toy_proejct.ui.component.CommonComponent
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(viewModel: DetailViewModel, url: String, back:() -> Unit) {

    val coroutineScope = rememberCoroutineScope() //코루틴 생성
    val isLoading by viewModel.isLoading //로딩 확인

    LaunchedEffect(true){
        coroutineScope.launch{viewModel.getDetailInfo(url)}
    }

    if(isLoading) {
        CommonComponent.LoadingSpinner()
    }
    else{
        DetailComponent(viewModel, back, url, itemList = viewModel.detailInfo.value)
    }
}

@Composable
private fun DetailComponent(
    viewModel:DetailViewModel,
    back: () -> Unit,
    url: String?,
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
                text = itemList.title, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center , style = MaterialTheme.typography.h5
            )
        }

            AsyncImage(
                model = itemList.image, //
                contentDescription = "image",
                modifier = Modifier.fillMaxWidth()
            )

                Text(
            text = "쇼핑몰 정보", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5

        )

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
            .height(60.dp)
            .clickable {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(item.link)
                    )
                )
            }
    ) {

            Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Center) {
                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = " ${item.price}원",
                        style = MaterialTheme.typography.h6,
                        color = Color.Blue

                    )
                }
            }


    }
}