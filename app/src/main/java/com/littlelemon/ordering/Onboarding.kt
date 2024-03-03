@file:OptIn(ExperimentalMaterial3Api::class)

package com.littlelemon.ordering

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.littlelemon.ordering.ui.theme.Green
import com.littlelemon.ordering.ui.theme.Red
import com.littlelemon.ordering.ui.theme.Yellow

@Composable
fun OnboardingScreen(navController: NavHostController, context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf("")
    }
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
        )
        Text(
            text = "Let's get to know you",
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Green)
                .wrapContentSize(Alignment.Center)
        )
        Text(
            text = "Personal information",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 24.dp, top = 48.dp)
        )
        Text(
            text = error,
            fontSize = 12.sp,
            color = Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp)
        )
        Column {
            Text(
                text = "First name",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { newText -> firstName = newText },
                placeholder = { Text(text = "First name") },
                textStyle = TextStyle(fontSize = 12.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                    .height(50.dp)
            )
            Text(
                text = "Last name",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { newText -> lastName = newText },
                textStyle = TextStyle(fontSize = 12.sp, color = Color.Black),
                placeholder = { Text(text = "Last name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                    .height(50.dp)
            )
            Text(
                text = "Email",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { newText -> email = newText },
                textStyle = TextStyle(fontSize = 12.sp, color = Color.Black),
                placeholder = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                    .height(50.dp)
            )
        }

        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    error = "Registration failed. Please enter all data."
                } else {
                    error = ""
                    sharedPreferences
                        .edit()
                        .putString("first-name", firstName)
                        .putString("last-name", lastName)
                        .putString("email", email)
                        .apply()
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(Home.route)
                }
            },
            colors = ButtonDefaults.buttonColors(Yellow),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Register",
                color = Color.Black
            )
        }

    }
}
