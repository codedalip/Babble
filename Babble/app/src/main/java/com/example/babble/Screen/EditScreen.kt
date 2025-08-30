package com.example.babble.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.buildSpannedString
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.babble.R
import com.example.babble.item.EventViewModel
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID


private fun saveImageToInternalStorage(context: Context, uri: Uri): String?{
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "event_image_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        Uri.fromFile(file).toString()
    }catch (e:Exception){
        e.printStackTrace()
        null
    }
}
@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavController, viewModel: EventViewModel, eventId: Int) {
    val eventToEdit by viewModel.getEventById(eventId).collectAsState(initial = null)

    var title by remember(eventToEdit) { mutableStateOf(eventToEdit?.title ?: "") }
    var desc by remember(eventToEdit) { mutableStateOf(eventToEdit?.description ?: "") }
    var location by remember(eventToEdit) { mutableStateOf(eventToEdit?.location ?: "") }
    var organizer by remember(eventToEdit) {
        mutableStateOf(
            eventToEdit?.organizingAuthority ?: ""
        )
    }
    var imageUri by remember(eventToEdit) { mutableStateOf(eventToEdit?.photoUri?.let {Uri.parse(it)})}
    var imageChanged by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri ->
            imageUri = uri
            imageChanged = true
        }
    )
    var date by remember (eventToEdit){ mutableStateOf(eventToEdit?.date?: "")}
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (eventToEdit == null) {
                Text(stringResource(R.string.loading_event), style = MaterialTheme.typography.bodyMedium)

            } else {
                OutlinedTextField(value = title, onValueChange = { title = it }, textStyle = MaterialTheme.typography.bodyMedium, label = {
                    Text(stringResource(R.string.title), style = MaterialTheme.typography.bodyMedium)
                }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = desc, onValueChange = { desc = it }, textStyle = MaterialTheme.typography.bodyMedium,label = {
                    Text(stringResource(R.string.description), style = MaterialTheme.typography.bodyMedium)
                }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = location, onValueChange = { location = it },textStyle = MaterialTheme.typography.bodyMedium, label = {
                    Text(stringResource(R.string.location), style = MaterialTheme.typography.bodyMedium)
                }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = organizer,
                    onValueChange = { organizer = it },textStyle = MaterialTheme.typography.bodyMedium,
                    label =
                    { Text(stringResource(R.string.organizing_authority), style = MaterialTheme.typography.bodyMedium) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row{
                    Text(
                        text = if(date.isEmpty()) "Select Date" else "Date: $date",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 13.dp, end = 20.dp)
                    )
                    Button(onClick = {showDatePicker = true}) {
                        Text(stringResource(R.string.change_date))
                    }
                }
                if(imageUri != null){
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Button(onClick = {imagePicker.launch("image/*")}
                , modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 28.dp)) {
                    Text(stringResource(R.string.change_image), style = MaterialTheme.typography.bodyMedium)
                }
                Button(
                    onClick = {
                        val finalImageUriString = if(imageChanged){
                            imageUri?.let { saveImageToInternalStorage(context, it) }
                        }else{
                            imageUri?.toString()
                        }
                        val updatedEvent = eventToEdit!!.copy(
                            title = title,
                            description = desc,
                            location = location,
                            organizingAuthority = organizer,
                            date = date,
                            photoUri = finalImageUriString
                        )
                        viewModel.updateEvent(updatedEvent)
                        navController.popBackStack()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.updateevent), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
    if (showDatePicker){
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {showDatePicker = false},
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDateMillis = datePickerState.selectedDateMillis
                        if(selectedDateMillis != null){
                            val selectedDate = Instant.ofEpochMilli(selectedDateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
                            date = selectedDate.format(formatter)
                        }
                        showDatePicker = false
                    }

                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = {showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) { DatePicker(state = datePickerState) }
    }
}