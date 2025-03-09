
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yarendemirkaya.waterreminder.R

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "home"),
        BottomNavItem("Statistics", R.drawable.ic_stats, "statistics"),
        BottomNavItem("Settings", R.drawable.ic_settings, "settings"),
        BottomNavItem("Profile", R.drawable.ic_profile, "profile"),
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 8.dp, end = 8.dp)
                .clip(RoundedCornerShape(18.dp))
                .border(2.dp, colorResource(id = R.color.app_color), RoundedCornerShape(18.dp))
                .shadow(16.dp, RoundedCornerShape(18.dp)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentDestination?.route == item.route
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                if (currentDestination?.route != item.route) {
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                            .padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(28.dp),
                            tint = if (isSelected) colorResource(id = R.color.app_color) else Color.Gray
                        )
                        Text(
                            text = item.title,
                            fontSize = 12.sp,
                            color = if (isSelected) colorResource(id = R.color.app_color) else Color.Gray
                        )
                    }
                }
            }
        }
    }
}

data class BottomNavItem(val title: String, val icon: Int, val route: String)
