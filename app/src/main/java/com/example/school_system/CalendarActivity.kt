package com.example.school_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme
import kotlinx.coroutines.launch

class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                CalendarScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu {}
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            CalendarTopBar(
                onMenuClick = { scope.launch { drawerState.open() } }
            )

            StaticCalendarUI()
        }
    }
}

/* -------------------------------------------------------------------------- */
/*                                   TOP BAR                                  */
/* -------------------------------------------------------------------------- */

@Composable
fun CalendarTopBar(onMenuClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF8B0000))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color.White,
            modifier = Modifier.clickable { onMenuClick() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(4.dp)
        )
    }
}

/* -------------------------------------------------------------------------- */
/*                         STATIC CALENDAR UI (NO LOGIC)                      */
/* -------------------------------------------------------------------------- */

@Composable
fun StaticCalendarUI() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        // MONTH TITLE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("<", fontSize = 20.sp)
            Text(
                "OCTOBER 2025",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(">", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // WEEK LABELS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach {
                Text(it, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // STATIC CALENDAR GRID (HARDCODED)
        val weekRows = listOf(
            listOf("",  "",  "",  "1", "2", "3", "4"),
            listOf("5", "6", "7", "8", "9", "10", "11"),
            listOf("12", "13", "14", "15", "16", "17", "18"),
            listOf("19", "20", "21", "22", "23", "24", "25"),
            listOf("26", "27", "28", "29", "30", "31", "1")
        )

        weekRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.forEach { day ->
                    StaticCalendarDay(day)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // EVENTS SECTION
        StaticCalendarEvents()
    }
}

/* -------------------------------------------------------------------------- */
/*                         STATIC CALENDAR DAY UI                             */
/* -------------------------------------------------------------------------- */

@Composable
fun StaticCalendarDay(day: String) {

    // COLORS BASED ON SPECIAL DAYS
    val bg = when (day) {
        "20" -> Color(0xFF19C300)  // green
        "8", "23" -> Color(0xFF8B0000) // red
        "4", "11", "18", "25" -> Color(0xFFE0EAFF) // Sunday blue
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        if (day.isNotEmpty()) {
            Text(
                text = day,
                color = if (bg != Color.Transparent && bg != Color(0xFFE0EAFF)) Color.White else Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* -------------------------------------------------------------------------- */
/*                              EVENTS SECTION                                */
/* -------------------------------------------------------------------------- */

@Composable
fun StaticCalendarEvents() {

    // GREEN EVENT BOX
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF00CC55))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Festival & Holidays", fontWeight = FontWeight.Bold, color = Color.White)
        Text("01", color = Color.White, fontWeight = FontWeight.Bold)
    }

    // RED EVENT BOX
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFB20000))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Absent", fontWeight = FontWeight.Bold, color = Color.White)
        Text("02", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

/* -------------------------------------------------------------------------- */
/*                                 DRAWER                                     */
/* -------------------------------------------------------------------------- */

//@Composable
//fun DrawerMenu(onSelect: (String) -> Unit) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxHeight()
//            .width(270.dp)
//            .background(Color(0xFF8B0000))
//            .padding(16.dp),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//
//        Column {
//            DrawerItem("Dashboard", R.drawable.ic_dashboard)
//            DrawerItem("Teacher", R.drawable.ic_teacher)
//            DrawerItem("Calendar", R.drawable.ic_calendar)
//            DrawerItem("Schedule", R.drawable.ic_schedule)
//        }
//
//        DrawerItem("Log Out", R.drawable.ic_logout, isLogout = true)
//    }
//}

@Composable
fun DrawerItem(
    title: String,
    icon: Int,
    isLogout: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(if (isLogout) Color(0xFFFDECEC) else Color.White)
            .padding(14.dp)
            .clickable { }
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = title,
            tint = if (isLogout) Color.Red else Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isLogout) Color.Red else Color.Black
        )
    }
}
