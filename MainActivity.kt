package com.example.mreminder3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mreminder3.ui.theme.MReminderTheme
import com.example.mreminder3.view.MedicineViewModel
import com.example.mreminder3.view.ReminderScreen
import com.example.mreminder3.view.AddReminderScreen
import com.example.mreminder3.notification.NotificationHelper

class MainActivity : ComponentActivity() {
    private val medicineViewModel: MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestExactAlarmPermission()
        }

        NotificationHelper.createNotificationChannel(this)

        setContent {
            MReminderTheme {
                MReminderApp(medicineViewModel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission.
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestExactAlarmPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission.
            requestPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MReminderApp(medicineViewModel: MedicineViewModel) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("MReminder") }) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "reminder_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("reminder_list") {
                ReminderScreen(medicineViewModel, navController)
            }
            composable("add_reminder") {
                AddReminderScreen(medicineViewModel, navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MReminderAppPreview() {
    MReminderTheme {
        MReminderApp(medicineViewModel = viewModel())
    }
}
