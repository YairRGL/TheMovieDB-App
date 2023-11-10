package com.example.themoviedb.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.themoviedb.navigation.TheMovieDBScreens
import com.example.themoviedb.utils.BottomNavigationItem

@Composable
fun TheMovieDBBottomBar(navController: NavController, selectedItemIndex: Int = 0) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(selectedItemIndex)
    }

    val items = listOf(
        BottomNavigationItem(
            title = TheMovieDBScreens.MainScreen.name,
            displayTitle = "Movies",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = TheMovieDBScreens.UserScreen.name,
            displayTitle = "User",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        containerColor = Color(0xFF0A0A0A),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.popBackStack()
                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.displayTitle, color = Color.White)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
            )
        }
    }
}
