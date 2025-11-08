package com.example.school_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // <--- ADDED
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // <--- CHANGED: Use Material 3 library
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // <--- ADDED: Use AutoMirrored version
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext

// Note: You must remove the following unused Material 2 imports if they exist:
// import androidx.compose.material.*
// import androidx.compose.material.icons.filled.ArrowBack <-- REMOVE

data class ScheduleItem(
    val timeStart: String,
    val timeEnd: String,
    val subject: String,
    val className: String,
    val room: String
)

@ExperimentalMaterial3Api
class ScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // FIX: You should use your app's theme here, e.g., SchoolsystemTheme 
            // If you don't have one, just use MaterialTheme from Material 3
            MaterialTheme {
                ScheduleScreen()
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ScheduleScreen() {
    val days = listOf("20\nMon", "21\nTue", "22\nWed", "23\nThu", "24\nFri", "25\nSat")
    var selectedDay by remember { mutableIntStateOf(0) }

    val scheduleList = remember {
        listOf(
            ScheduleItem("7:30", "7:30", "Mobile App", "M2 103B", "ROOM 103B"),
            ScheduleItem("9:15", "10:45", "OOAD", "M1 102B", "ROOM 102B"),
            ScheduleItem("2:00", "3:30", "Mobile App", "A2 103B", "ROOM 103A"),
            ScheduleItem("5:30", "7:00", "OOAD", "M2 103B", "ROOM 103B"),
            ScheduleItem("7:15", "8:45", "Mobile App", "E5 108B", "ROOM 108B")
        )
    }

    // FIX: Use Material 3 Scaffold
    Scaffold(
        topBar = {
            // FIX: Use Material 3 TopAppBar, CenterAlignedTopAppBar, or SmallTopAppBar
            CenterAlignedTopAppBar(
                title = { Text("Schedule", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { (days as? ComponentActivity)?.finish() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)) {

            // Days Row (Logic unchanged)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                days.forEachIndexed { index, day ->
                    val isSelected = selectedDay == index
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(45.dp)
                            .height(65.dp)
                            .background(
                                if (isSelected) Color(0xFF8B0000) else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(8.dp)
                            .clickable { selectedDay = index }
                    ) {
                        Text(
                            text = day,
                            color = if (isSelected) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // FIX: Use Material 3 Divider
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Schedule List
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(scheduleList) { item ->
                    ScheduleCard(item)
                }
            }
        }
    }
}

@Composable
fun ScheduleCard(item: ScheduleItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Time (Unchanged)
        Text(
            text = "${item.timeStart}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Subject Card (Unchanged)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF8B0000), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.subject,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.className,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = item.room,
                color = Color.Gray,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // FIX: Use Material 3 Divider
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}