package presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.db.Bp
import kotlinx.coroutines.launch
import presentation.vm.CustomViewModel
import presentation.vm.CustomViewModelGet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
/**
 * Example of using Jetpack Compose with a database.
 * Demonstrates how to fetch data from a database and display it in a list,
 * as well as how to insert, delete, and update records.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun dbExample() {
    // Instances of view models for accessing the database
    val myVM = CustomViewModel()
    val myVMGet = CustomViewModelGet()
    // State variables for storing the results and loading status
    var result by remember { mutableStateOf(listOf<Bp>()) }
    var isLoading by remember { mutableStateOf(false) }
    // State variables for storing the details of the selected record
    var id by remember { mutableStateOf(0L) }
    var idsbp by remember { mutableStateOf(0L) }
    var iddbp by remember { mutableStateOf(0L) }
    var iddatetime by remember { mutableStateOf("") }
    // State variables for storing the values entered in the text fields
    var sbp by remember { mutableStateOf(TextFieldValue("0")) }
    var dbp by remember { mutableStateOf(TextFieldValue("0")) }
    // Coroutine scope for launching coroutines
    val coroutineScope = rememberCoroutineScope()
    // Fetch all records from the database
    myVMGet.getAll() {
        coroutineScope.launch {
            myVMGet.myBpStateFlow.collect { it ->
                isLoading = true
                if (it != null) {
                    result = it
                }
                isLoading = false

            }
        }
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        // Display the list of records and buttons for manipulating the data
        Row {
            //
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(result) {
                    Text(text = "$it",
                        modifier = Modifier.onClick {
                            id = it.id
                            idsbp = it.sbp!!
                            iddbp = it.dbp!!
                            iddatetime = it.event_time
                        }
                    )
                }
            }
            //
            Column(modifier = Modifier.weight(1f)) {
                Button(onClick = {
                    myVM.insertBp(sbp.text.toInt(), dbp.text.toInt(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString())
                }) {
                    Text(text = "Add")
                }
                Button(onClick = {
                    myVM.deleteAll()
                }) {
                    Text(text = "Delete All")
                }
                Button(onClick = {
                    myVM.dropBpTable()
                }) {
                    Text(text = "Drop Table")
                }
                Button(onClick = {
                    myVM.deleteBp(id.toInt())
                }) {
                    Text(text = "Delete Item")
                }
                Button(onClick = {
                    // exclude id=0 as updating id=0 will add an item
                    if (id.toInt() != 0) {
                        myVM.updateBp(id.toInt(), sbp.text.toInt(), dbp.text.toInt(), LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString())
                    }
                }) {
                    Text(text = "Update Item")
                }
            }
            //
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Selected Item", modifier = Modifier.background(color= Color.Yellow))
                Spacer(modifier = Modifier.size(32.dp))
                Text(text = "id = $id , sbp = $idsbp, dbp = $iddbp \n$iddatetime", modifier = Modifier.background(color= Color.LightGray))
                Spacer(modifier = Modifier.size(32.dp))
                TextField(
                    label = { Text("SBP") },
                    value = sbp,
                    singleLine = true,
                    onValueChange = { sbp = it },
                )
                TextField(
                    label = { Text("DBP") },
                    value = dbp,
                    singleLine = true,
                    onValueChange = { dbp = it },
                )
            }
        }
    }
}