package presentation.vm

import com.db.Bp
import domain.usecases.GetAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
/**
 * ViewModel class responsible for getting all blood pressure records from the database.
 * Uses a repository to access the database and a state flow to observe changes to the data.
 */
class CustomViewModelGet {

    // State flow for observing changes to the blood pressure records
    val myBpStateFlow = MutableStateFlow<List<Bp>?>(emptyList())
    /**
     * Gets all blood pressure records from the database.
     * Collects the data from the state flow and updates the state flow with the new data.
     *
     * @param callback Callback function to be executed after data is fetched
     */
    fun getAll(callback: () -> Unit) {
        val myBpFlow: Flow<List<Bp>?> = GetAll().invoke()

        CoroutineScope(Dispatchers.IO).launch {
            myBpFlow.collect {
                myBpStateFlow.value = it
            }
        }
        callback()
    }
}