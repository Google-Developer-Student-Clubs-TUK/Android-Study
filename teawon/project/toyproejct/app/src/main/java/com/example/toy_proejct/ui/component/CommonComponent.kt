package com.example.toy_proejct.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object CommonComponent {
    @Composable
    fun ButtomNavbar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "메뉴",
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "홈",
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "설정",
                modifier = Modifier.weight(1f)
            )
        }
    }
}