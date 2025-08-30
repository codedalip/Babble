package com.example.babble.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Outline
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.babble.R
import com.example.babble.data.Event
import com.example.babble.item.EventViewModel
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale
import java.time.format.DateTimeFormatter
import java.util.UUID


private fun saveImageToInternalStorage(context: Context, uri: Uri): String?{
    return try{
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "event_image_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        Uri.fromFile(file).toString()
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}
@SuppressLint("NewApi", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavController, viewModel: EventViewModel) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var organizer by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var date by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri ->
            imageUri = uri
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                title = { Text(text = stringResource(R.string.post_event),
                    style = MaterialTheme.typography.bodySmall) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title), style = MaterialTheme.typography.bodyMedium) },
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = desc,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { desc = it },
                label = { Text(stringResource(R.string.description), style = MaterialTheme.typography.bodyMedium) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = location,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { location = it },
                label = { Text(stringResource(R.string.location), style = MaterialTheme.typography.bodyMedium) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = organizer,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { organizer = it },
                label = { Text(stringResource(R.string.organizing_authority), style = MaterialTheme.typography.bodyMedium) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if(imageUri != null){
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = stringResource(R.string.event),
                    modifier = Modifier.size(200.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop
                )
            }else{
                Button(onClick = {imagePicker.launch("image/*")}){
                    Text(stringResource(R.string.select_image), style = MaterialTheme.typography.bodyMedium)
                }
            }

            Row {
                Text(
                    text = if(date.isEmpty()) stringResource(R.string.select_date) else "Date: $date",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 13.dp, end = 20.dp)
                )
                Button(onClick = {showDatePicker = true}) {
                    Text(stringResource(R.string.select_date), style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            var navigateBack by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    val permanentImageUriString = imageUri?.let {
                        saveImageToInternalStorage(context, it)
                    }
                    val eventDate = if(date.isNotEmpty()){
                        date
                    }else {
                    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
                        LocalDate.now().format(formatter)}
                    val newEvent = Event(
                        title = title,
                        description = desc,
                        location = location,
                        date = eventDate,
                        organizingAuthority = organizer,
                        photoUri = permanentImageUriString
                    )
                    viewModel.addEvent(newEvent)
                    navigateBack = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.post_event), style = MaterialTheme.typography.bodyMedium)
            }
            if (navigateBack){
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }
        if(showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = {showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedDateMillis = datePickerState.selectedDateMillis
                            if(selectedDateMillis != null){
                                val selectedDate = Instant.ofEpochMilli(selectedDateMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                val formatter = DateTimeFormatter.ofPattern("MM/ dd/ yyyy", Locale.getDefault())
                                date = selectedDate.format(formatter)
                            }
                            showDatePicker =false
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
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}