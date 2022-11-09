package com.example.gdsc_androidstudy // ktlint-disable package-name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.* // ktlint-disable no-wildcard-imports
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gdsc_androidstudy.main.CommentScreen
import com.example.gdsc_androidstudy.main.MyPageScreen
import com.example.gdsc_androidstudy.main.post.PostPage

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object PostPage : BottomNavItem("메인 포스트 화면", Icons.Filled.Home, "Post")
    object SearchPage : BottomNavItem("검색 화면", Icons.Filled.Search, "SEARCH")
    object ShortVideoPage : BottomNavItem("클립 화면", Icons.Filled.MailOutline, "SHORTVIDEO")
    object ShopPage : BottomNavItem("쇼핑 화면", Icons.Filled.ShoppingCart, "SHOP")
    object MyPage : BottomNavItem("마이페이지", Icons.Filled.Person, "MYPAGE")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Scaffold(bottomBar = {
                BottomNavigationBar(
                    items = listOf(BottomNavItem.PostPage, BottomNavItem.SearchPage, BottomNavItem.ShortVideoPage, BottomNavItem.ShopPage, BottomNavItem.MyPage),
                    navController = navController
                )
            }) {
                Screen(startRoute = BottomNavItem.PostPage.route, navController, Modifier.padding(it))
            }
        }
    }
}

@Composable
fun Screen(startRoute: String, navController: NavHostController, modifier: Modifier = Modifier) {
    // NavHost 로 네비게이션 결정
    NavHost(navController, startRoute) {
        composable(BottomNavItem.PostPage.route) {
            PostPage(navController)
        }
        composable(BottomNavItem.MyPage.route) {
            MyPageScreen(navHostController = navController)
        }
        composable(BottomNavItem.SearchPage.route) {
            PostPage(navController)
        }
        composable(BottomNavItem.ShopPage.route) {
            PostPage(navController)
        }
        composable(BottomNavItem.ShortVideoPage.route) {
            PostPage(navController)
        }
        composable("댓글") {
            CommentScreen(postId = 1, navController)
        }
    }
}

@Composable
fun BottomNavigationBar(items: List<BottomNavItem>, navController: NavHostController, modifier: Modifier = Modifier) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(backgroundColor = Color.White, modifier = modifier) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }
            )
        }
    }
}
