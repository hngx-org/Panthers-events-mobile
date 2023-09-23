package com.panther.events_app.presentation.screens.my_people

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.panther.events_app.R
import com.panther.events_app.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddMyPeopleScreen(
//    navController : NavController,t
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
    ) {
        ExtendedMyPeopleSection(modifier = modifier.padding(it))
    }


}

@Composable
fun ExtendedMyPeopleSection(
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
            MyPeopleItem(
                text = "YBNL Mafia",
                icon = R.drawable.glasses_vector,
                color = Color(0xFFFFB700),
                onNextClick = {}
            )
            MyPeopleItem(
                text = "Techies",
                icon = R.drawable.people_vector,
                color = Color(0xFFFF5500),
                isEventPresent = true,
                onNextClick = {}
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyPeopleItem(
                text = "Medical Guys",
                icon = R.drawable.medical_vector,
                color =  Color(0xFF00C352),
                onNextClick = {}
            )
            MyPeopleItem(
                text = "Ladies in Tech",
                icon = R.drawable.ldies_in_tech_vector,
                color = Color(0xFF3300FF),
                isEventPresent = false,
                onNextClick = {}
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyPeopleItem(
                text = "Discord",
                icon = R.drawable.discord_vector,
                color = Color(0xFFFF474F),
                onNextClick = {}
            )
            MyPeopleItem(
                text = "Lagos Tech",
                icon = R.drawable.lagos_tech_vector,
                color = Color(0xFF9823C9),
                isEventPresent = false,
                onNextClick = {}
            )
        }
    }
}