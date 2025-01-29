package com.example.hhuz


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hhuz.page.FavoriteScreen
import com.example.hhuz.page.ProfileScreen
import com.example.hhuz.page.ResponsesScreen
import com.example.hhuz.page.SearchScreen
import com.example.hhuz.page.SmsScreen
import com.example.hhuz.ui.theme.Black
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navItemList = listOf(
        NavItem("Поиск", R.drawable.serchdf, 0),
        NavItem("Избранное", R.drawable.heart, 5),
        NavItem("Отклики", R.drawable.otklik, 0),
        NavItem("Сообщения", R.drawable.sms, 0),
        NavItem("Профиль", R.drawable.profile, 0),
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // ContentScreen barcha joyni egallashi uchun weight(1f) qo‘shildi
        Box(modifier = Modifier.weight(1f)) {
            ContentScreen(selectedIndex = selectedIndex)
        }

        NavigationBar(containerColor = Color.Black) {
            navItemList.forEachIndexed { index, navItem ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    icon = {
                        BadgedBox(badge = {
                            if (navItem.badgeCount > 0)
                                Badge {
                                    Text(text = navItem.badgeCount.toString())
                                }
                        }) {
                            Icon(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = navItem.label,
                                modifier = Modifier.size(30.dp),
                                tint = if (selectedIndex == index) Color.Blue else Color.Unspecified
                            )
                        }
                    },
                    label = {
                        Text(
                            text = navItem.label,
                            fontSize = 10.sp,
                            color = if (selectedIndex == index) Color.Blue else Color.Unspecified
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> SearchScreen()
        1 -> FavoriteScreen()
        2 -> ResponsesScreen()
        3-> SmsScreen()
        4-> ProfileScreen()
    }
}