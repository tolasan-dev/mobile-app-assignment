package com.example.school_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.school_system.ui.theme.SchoolsystemTheme

// Define the colors used in the design
val primary: Color = Color(0xFF8B0000)
val ExamCardColor = Color(0xFFD4E6F1) // Light blue/white
val HomeworkCardColor = Color(0xFF85C1E9) // Mid blue
val ScheduleCardColor = Color(0xFFF7DC6F) // Yellow
val AttendanceCardColor = Color(0xFFE5E7E9) // Light grey

// Assuming you have image resources for the icons (e.g., R.drawable.ic_exam)
// Since I can't access your R file, I will use placeholders/Material icons and generic names.

class MainProfessorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolsystemTheme {
                MainProfessorScreen()
            }
        }
    }
}

data class DashboardItem(
    val title: String,
    val color: Color,
    val iconContent: @Composable () -> Unit,
    val onClick: () -> Unit = {}
)

@Composable
fun MainProfessorScreen() {
    Scaffold(
        topBar = { ProfessorTopBar() },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ProfessorWelcomeHeader()
            Spacer(modifier = Modifier.height(24.dp))
            DashboardGrid()
        }
    }
}

@Composable
fun ProfessorTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White, modifier = Modifier.size(28.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(28.dp))
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(28.dp))
            // Profile Icon Placeholder
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { /* Handle profile click */ }
            )
        }
    }
}

@Composable
fun ProfessorWelcomeHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Class M2 103",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Change Class", tint = primary)
            Text(
                text = "Change class",
                fontSize = 14.sp,
                color = primary,
                modifier = Modifier.padding(start = 4.dp).clickable { /* Handle change class */ }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture Placeholder (use R.drawable.ic_professor_avatar if available)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Welcome.", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Tea. Kdam Prai", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Mobile App", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun DashboardGrid() {
    val items = listOf(
        DashboardItem("Exam", ExamCardColor, { /* R.drawable.ic_exam */ ProfessorCardIconPlaceholder(IconType.Exam) }),
        DashboardItem("Home work", HomeworkCardColor, { /* R.drawable.ic_homework */ ProfessorCardIconPlaceholder(IconType.Homework) }),
        DashboardItem("Schedule", ScheduleCardColor, { /* R.drawable.ic_schedule */ ProfessorCardIconPlaceholder(IconType.Schedule) }),
        DashboardItem("Attendents", AttendanceCardColor, { /* R.drawable.ic_attendance */ ProfessorCardIconPlaceholder(IconType.Attendance) })
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items.size) { index ->
            DashboardCard(item = items[index])
        }
    }
}

enum class IconType { Exam, Homework, Schedule, Attendance }

// Placeholder to represent the custom icons in the image
@Composable
fun ProfessorCardIconPlaceholder(type: IconType) {
    val (_, _) = when (type) {
        IconType.Exam -> Pair(Icons.Default.Settings, Color.Blue)
        IconType.Homework -> Pair(Icons.Default.Settings, Color.White)
        IconType.Schedule -> Pair(Icons.Default.Settings, primary)
        IconType.Attendance -> Pair(Icons.Default.Settings, Color.Black)
    }
    // In a real app, replace this with your Image composable using painterResource
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        // Placeholder for complex custom drawable icon
        Text(
            text = when (type) {
                IconType.Exam -> "A+"
                IconType.Homework -> "📚"
                IconType.Schedule -> "📅"
                IconType.Attendance -> "✅"
            },
            fontSize = 48.sp,
            color = Color.Black
        )
    }
}


@Composable
fun DashboardCard(item: DashboardItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = item.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clickable(onClick = item.onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item.iconContent()
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainProfessorScreenPreview() {
    SchoolsystemTheme {
        MainProfessorScreen()
    }
}
