package presentation.vm

import com.db.Bp
import data.local.BpDatabase
import data.repositoryImpl.BpRepositoryImpl
import kotlinx.coroutines.*


class CustomViewModel {
    private val bpAccess = BpRepositoryImpl(BpDatabase.getDatabaseDao())

    @OptIn(DelicateCoroutinesApi::class)
    fun insertBp(sbp: Int, dbp: Int, eventTime: String) = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.insertBp(sbp, dbp, eventTime)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateBp(id: Int, sbp: Int, dbp: Int, eventTime: String) = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.updateBp(id, sbp, dbp, eventTime)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteBp(id: Int) = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.deleteBp(id)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun selectBp(id: Int): Deferred<Bp?> = GlobalScope.async(Dispatchers.IO) {
        bpAccess.selectBp(id)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.deleteAll()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun selectAll(): Deferred<List<Bp>?> = GlobalScope.async(Dispatchers.IO) {
        bpAccess.selectAll()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun dropBpTable() = GlobalScope.launch(Dispatchers.IO) {
        bpAccess.dropBpTable()
    }
}