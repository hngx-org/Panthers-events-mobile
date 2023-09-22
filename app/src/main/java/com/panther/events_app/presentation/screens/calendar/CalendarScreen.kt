package com.panther.events_app.presentation.screens.calendar

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = Color(0xFFFFF2E9),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Calendar",
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 32.sp,
//                            fontFamily = FontFamily(Font(R.font.space grotesk)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFFFF2E9)
                ),
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(thickness = 0.5.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Calendar(modifier = Modifier.padding(20.dp))
//            Spacer(modifier = Modifier.height(5.dp))
            EventItem(modifier = Modifier.padding(20.dp))
        }

    }
}


@Composable
fun Calendar(
    modifier: Modifier = Modifier,
) {

    var date by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.5.dp, color = Color.Black)
            .width(380.dp)
            .height(340.dp)
            .background(color = Color(0xFFFFFFFF))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(factory = { CalendarView(it) }, update = {
                it.setOnDateChangeListener { calendarView, year, month, day ->
                    date = "$day - ${month + 1} - $year"
                }
            })
            Text(text = date)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun EventItem(
    modifier: Modifier = Modifier
)  {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.5.dp, color = Color.Black)
            .width(380.dp)
            .height(55.dp)
            .background(color = Color(0xFFFFFFFF))
//            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 12.dp, start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(1.dp)
            ) {
                Text(
                    text = "Movie Night",
                    style = TextStyle(
                        fontSize = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.spacegrotesk_semibold)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF33313E),
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = "Genesis Cinemas, Festac",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF84838B),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Column {
                Text(
                    text = "8:30 PM",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF33313E),
                        textAlign = TextAlign.Right,
                        letterSpacing = 0.3.sp,
                    )
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text(
                    text = "9:45 PM",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.lato)),
                        fontWeight = FontWeight(400),
                        color = Color(0xB233313E),
                        textAlign = TextAlign.Right,
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        }
    }
}