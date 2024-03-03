package com.littlelemon.ordering

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, databaseItems: State<List<MenuItemRoom>>) {
    var search by remember {
        mutableStateOf("")
    }
    var index by remember {
        mutableIntStateOf(0)
    }
    val menuItem = databaseItems.value
    var chooseCategory by remember {
        mutableIntStateOf(0)
    }
    var menuItems = when (chooseCategory) {
        0 -> menuItem
        1 -> menuItem.filter { it.category == "starters" }
        2 -> menuItem.filter { it.category == "mains" }
        3 -> menuItem.filter { it.category == "desserts" }
        else -> menuItem
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo image",
                modifier = Modifier
                    .size(200.dp, 65.dp)
                    .padding(end = 48.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(65.dp, 65.dp)
                    .padding(16.dp)
                    .clickable { navController.navigate(Profile.route) }
                    .clip(shape = CircleShape)

            )
        }
        Column(
            modifier = Modifier
                .background(Color(0xFF495E57))
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Little Lemon",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF4CE14)
            )
            Text(
                text = "Chicago",
                fontSize = 24.sp,
                color = Color(0xFFEDEFEE)
            )
            Row(
                modifier = Modifier
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color(0xFFEDEFEE),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 28.dp)
                        .fillMaxWidth(0.6f)
                )
                Image(
                    painter = painterResource(id = R.drawable.upperpanelimage),
                    contentDescription = "Upper Panel Image",
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                )
            }
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Enter Search Phrase") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
        }

        Column {
            Text(
                text = "ORDER FOR DELIVERY!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 8.dp, bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )

            TabRow(
                selectedTabIndex = index,
                containerColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Button(
                    onClick = {
                        index = 0
                        chooseCategory = 0
                    },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .wrapContentSize(unbounded = true)
                        .padding(8.dp)

                ) {
                    Text(
                        text = "All",
                        color = Color(0xFF495E57),
                        fontSize = 10.sp
                    )

                }

                Button(
                    onClick = {
                        index = 1
                        chooseCategory = 1
                    },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .wrapContentSize(unbounded = true)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Starters",
                        color = Color(0xFF495E57),
                        fontSize = 10.sp
                    )
                }
                Button(
                    onClick = {
                        index = 2
                        chooseCategory = 2
                    },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .wrapContentSize(unbounded = true)
                        .padding(8.dp)


                ) {
                    Text(
                        text = "Mains",
                        color = Color(0xFF495E57),
                        fontSize = 10.sp
                    )

                }
                Button(
                    onClick = {
                        index = 3
                        chooseCategory = 3

                    },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .wrapContentSize(unbounded = true)
                        .padding(8.dp)

                ) {
                    Text(
                        text = "Desserts",
                        color = Color(0xFF495E57),
                        fontSize = 10.sp
                    )

                }

            }
            if (search.isNotEmpty()) {
                menuItems = menuItems.filter { it.title.contains(search, true) }
            }

            MenuItemsList(menuItems)
        }


    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .wrapContentSize()
                            .width(220.dp)

                    ) {
                        Text(text = menuItem.title, fontWeight = FontWeight.Bold)
                        Text(
                            text = menuItem.description,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "$" + (menuItem.price).toString(),
                            modifier = Modifier.padding(top = 16.dp)
                        )

                    }
                    GlideImage(
                        model = menuItem.image,
                        contentDescription = "Image of the dish",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
                Divider(
                    modifier = Modifier.padding(8.dp),
                    color = Color.LightGray
                )
            }
        )
    }
}