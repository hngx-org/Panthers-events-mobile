package com.panther.events_app.presentation.screens.my_people

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.events_app.R


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPeopleScreen(
    modifier: Modifier = Modifier,
) {

    Scaffold(
        containerColor = Color(0xFFFFF2E9),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .width(101.dp)
                            .height(26.dp),
                        text = "My People",
                        style = TextStyle(
                            fontSize = 20.sp,
//                            fontFamily = FontFamily(Font(R.font.spacegrotesk_semibold)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFFFF2E9)
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = Color.Black,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFFFFF2E9)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        MyPeopleSection(modifier = modifier.padding(it))
    }
}

@Composable
fun MyPeopleSection(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyPeopleItem(text = "YBNL Mafia", icon = R.drawable.glasses_vector, color = Color(0xFFFFB700))
            MyPeopleItem(text = "Techies", icon = R.drawable.people_vector, color = Color(0xFFFF5500), isEventPresent = true)
        }
    }
}

@Composable
fun MyPeopleItem(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    color: Color,
    isEventPresent: Boolean = false
) {

    Surface(
        modifier = modifier
            .height(154.dp)
            .width(168.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = color
//        ),
        color = color,
        shadowElevation = 6.dp
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                modifier= Modifier
//                    .padding(1.dp)
                    .width(78.57143.dp)
                    .height(55.dp),
//                    .background(color = Color(0xFF212121)),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .width(87.dp)
                    .height(20.dp),
                text = text,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
//                    fontFamily = FontFamily(Font(R.font.spacegrotesk_medium))
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isEventPresent){
                Surface (
                    modifier = modifier
                        .align(Alignment.End)
                        .height(18.dp)
                        .width(59.dp)
                        .fillMaxHeight(),
                    color = Color.Black
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            modifier = Modifier,
//                            .width(42.dp)
//                            .height(15.dp),
                            text = "2 events",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 10.sp,
//                                fontFamily = FontFamily(Font(R.font.spacegrotesk_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                }
            }


        }

    }
}

