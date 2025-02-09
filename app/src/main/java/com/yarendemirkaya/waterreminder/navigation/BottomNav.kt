package com.yarendemirkaya.waterreminder.navigation


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yarendemirkaya.waterreminder.R


@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "home"),
        BottomNavItem("Statistics", R.drawable.ic_stats, "statistics"),
        BottomNavItem("Settings", R.drawable.ic_settings, "settings"),
        BottomNavItem("Profile", R.drawable.ic_profile, "profile"),
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    BottomAppBar(
        modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = colorResource(id = R.color.app_color)),
    ) {
        items.forEach { item ->
            val isSelected = currentDestination?.route == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    if (currentDestination?.route != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.app_color),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = colorResource(id = R.color.app_color),
                    unselectedTextColor = Color.Gray,
                )
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: Int, val route: String)

