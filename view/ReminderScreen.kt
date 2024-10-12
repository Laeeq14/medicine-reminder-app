package com.example.mreminder3.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mreminder3.data.Medicine

@Composable
fun ReminderScreen(
    medicineViewModel: MedicineViewModel,
    navController: NavHostController
) {
    val medicines by medicineViewModel.allMedicines.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Reminders",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(medicines) { medicine ->
                ReminderItem(
                    medicine = medicine,
                    onDelete = {
                        medicineViewModel.delete(medicine)
                    }
                )
            }
        }
        Button(
            onClick = { navController.navigate("add_reminder") },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        ) {
            Text(text = "Add Reminder")
        }
    }
}


@Composable
fun ReminderItem(medicine: Medicine, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${medicine.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Dosage: ${medicine.dosage}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Time: ${java.text.DateFormat.getDateTimeInstance().format(java.util.Date(medicine.time))}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(
                    onClick = onDelete,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
