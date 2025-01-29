package com.example.hhuz.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hhuz.R
import com.example.hhuz.ui.theme.Black
import com.example.hhuz.ui.theme.Blue
import com.example.hhuz.ui.theme.Green
import com.example.hhuz.ui.theme.Grey1
import com.example.hhuz.ui.theme.Grey5
import com.example.hhuz.ui.theme.Shadows
import com.example.hhuz.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .background(Black)
            .padding(16.dp)
    ) {
        val jobItems = remember { mutableStateListOf("UI/UX Designer", "Android Developer", "Data Analyst", "UI/UX Designer", "Android Developer", "Data Analyst") }
        val likedItems = remember { mutableStateMapOf<String, Boolean>() }
        val currentItemIndex = remember { mutableStateOf(0) } // Hozirda ko'rinayotgan itemning indeksi
        val lazyListState = rememberLazyListState() // Lazy list state
        val coroutineScope = rememberCoroutineScope() // Coroutine scope yaratish

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 0.dp) // Bo'sh joy qo'ymaslik
        ) {
            // LazyRow uchun itemlar
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(jobItems) { item ->
                    Column(
                        Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(15.dp)) // Burchaklarni yumaloq qilish
                            .background(Grey1)
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vakansiya), // Rasmingizni shu yerga qo'ying
                            contentDescription = "Sample Image",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "Вакансии рядом с вами", color = White)
                        Text(text = "Поднять", color = Green)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Text(text = "Вакансии для вас", color = Color.White, fontSize = 25.sp, modifier = Modifier.padding(vertical = 10.dp))

            // LazyColumn uchun itemlar
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = lazyListState, // LazyColumn uchun state
            ) {
                items(jobItems) { jobTitle ->
                    JobItem(
                        jobTitle = jobTitle,
                        isLiked = likedItems[jobTitle] ?: false,
                        onLikeClicked = { likedItems[jobTitle] = !(likedItems[jobTitle] ?: false) }
                    )
                }
            }

            // Ko'rinadigan itemning indeksini olish va uni saqlash
            LaunchedEffect(lazyListState.firstVisibleItemIndex) {
                val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
                currentItemIndex.value = firstVisibleItemIndex + 1 // Hozirda ko'rinayotgan itemni yangilash
            }


            // Konsolga to'g'ri indeksni chiqarish
            println("You are at item ${currentItemIndex.value}")
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Ekranning pastki markaziga joylash
                .clickable {
                    // Pastki matnni bosganda LazyColumnni 2 itemga surish
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(currentItemIndex.value + 2) // Ikki item pastga surish
                    }
                }
        ) {
            // Qolgan vakansiyalarni ko'rsatish
            if (currentItemIndex.value < jobItems.size - 1) {
                Text(
                    text = "Еще ${jobItems.size - currentItemIndex.value-1} вакансии", // Qolgan itemlar soni
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Blue)
                        .padding(13.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun JobItem(jobTitle: String, isLiked: Boolean, onLikeClicked: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Grey1)
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Сейчас просматривает 1 человек", color = Color.Green)
                IconButton(onClick = onLikeClicked) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (isLiked) Color.Red else Color.Gray
                    )
                }
            }
            Text(
                text = jobTitle,
                color = Color.White,
                fontSize = 22.sp,
            )
            Text(
                text = "1500-2900 BR",
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(text = "Минск ", color = Color.White)
            Row {
                Text(text = "Мобирикс", color = Color.White, modifier = Modifier.padding(end = 5.dp))
                Image(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.job),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(20.dp)
                )
                Text(text = "Опыт от 1 года до 3 лет", color = Color.White)
            }
            Text(text = "Опубликовано 20 февраля", color = Color.White)
            Text(
                text = "Откликнуться",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Green)
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
