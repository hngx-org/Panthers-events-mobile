@file:OptIn(ExperimentalMaterial3Api::class)

package com.panther.events_app.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.events_app.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(){
    Scaffold(
        topBar = { TopAppBar()}
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(2.dp, color = Color.Black)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                    Row(modifier = Modifier.weight(0.9f)) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_sentiment_satisfied_24),
                            contentDescription = "profile icon"
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)){
                            Text(text = "Salome name", style = TextStyle(fontSize = 24.sp))
                            Text(text = "salome@gmail.com",style = TextStyle(fontSize = 20.sp))
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                        contentDescription = "right icon",
                        modifier = Modifier.weight(0.1f),
                        contentScale = ContentScale.Crop
                    )

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(2.dp, color = Color.Black)
            ) {
                SettingItem(
                    icon = R.drawable.baseline_notifications_none_24,
                    text = "Notification",
                    description = "Notification"
                )
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                SettingItem(
                    icon = R.drawable.baseline_privacy_tip_24,
                    text = "Privacy",
                    description = "privacy"
                )
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                SettingItem(
                    icon = R.drawable.baseline_palette_24,
                    text = "Appearance",
                    description = "Appearance"
                )
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp))
                SettingItem(
                    icon = R.drawable.baseline_language_24,
                    text = "Language and Region",
                    description = "Language and Region"
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(2.dp, color = Color.Black)
            ) {
                Card {
                    SettingItem(
                        icon = R.drawable.baseline_help_24,
                        text = "Help and Support",
                        description = "Help and Support"
                    )
                    Divider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    SettingItem(
                        icon = R.drawable.baseline_info_24,
                        text = "About",
                        description = "About"
                    )
                }
            }


            }
        }
    }

@Composable
fun TopAppBar() {
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Text(text = "Settings", style= TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Normal
        )
        )
    }
}

@Composable
fun SettingItem(
    @DrawableRes icon: Int,
    text : String,
    description : String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(0.9f)) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = description,
                contentScale = ContentScale.Crop
            )
            Text(text= text,
                style = TextStyle(
                    fontSize =20.sp
                ),
                modifier = Modifier.padding(start = 8.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription ="Forward arrow",
            modifier = Modifier.weight(0.1f),
            contentScale = ContentScale.Crop
        )
    }

}