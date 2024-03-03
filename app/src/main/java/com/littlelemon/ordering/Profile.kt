package com.littlelemon.ordering

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.littlelemon.ordering.ui.theme.Yellow

@Composable
fun ProfileScreen(context: Context, navController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo image",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp, 65.dp)
                .clickable { navController.navigate(Home.route) }

        )
        Column {

            Text(
                text = "Personal information:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
            )
            Text(
                text = "First name: ${sharedPreferences.getString("first-name", "")}",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Last name: ${sharedPreferences.getString("last-name", "")}",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "Email: "
                        + sharedPreferences.getString("email", ""),
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    sharedPreferences.edit().clear().apply()
                    navController.navigate(Onboarding.route)
                },
                colors = ButtonDefaults.buttonColors(Yellow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Log out",
                    color = Color.Black
                )
            }
        }
    }
}