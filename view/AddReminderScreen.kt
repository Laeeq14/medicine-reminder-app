package com.example.mreminder3.view

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.mreminder3.data.Medicine
import com.example.mreminder3.notification.NotificationHelper
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddReminderScreen(
    medicineViewModel: MedicineViewModel,
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var timeInMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    val context = LocalContext.current
    val notificationHelper = remember { NotificationHelper(context) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Medicine Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                showTimePicker(context) { selectedTimeInMillis ->
                    timeInMillis = selectedTimeInMillis
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 8.dp)
        ) {
            Text("Pick Time")
        }
        Text(
            text = "Selected Time: ${formatTime(timeInMillis)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = {
                val newMedicine = Medicine(name = name, time = timeInMillis, dosage = dosage)
                medicineViewModel.insert(newMedicine)
                notificationHelper.scheduleNotification(
                    notificationId = newMedicine.id,
                    title = "Medicine Reminder",
                    content = "It's time to take your medicine: $name",
                    timeInMillis = timeInMillis
                )
                navController.navigateUp()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        ) {
            Text("Add Reminder")
        }
    }
}

private fun showTimePicker(context: Context, onTimeSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            onTimeSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )
    timePickerDialog.show()
}

private fun formatTime(timeInMillis: Long): String {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return dateFormat.format(Date(timeInMillis))
}
