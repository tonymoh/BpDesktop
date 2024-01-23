package data.repositoryImpl

import com.db.Bp
import com.db.BpDatabaseQueries

class BpRepositoryImpl(private val myBpDatabaseQueries: BpDatabaseQueries) {

    fun insertBp(sbp: Int, dbp: Int, eventTime: String) {
        myBpDatabaseQueries.InsertBpByValues(null, sbp.toLong(), dbp.toLong(), eventTime)
    }

    fun updateBp(id:Int, sbp: Int, dbp: Int, eventTime: String) {
        myBpDatabaseQueries.InsertBpByValues(id.toLong(), sbp.toLong(), dbp.toLong(), eventTime)
    }

    fun deleteBp(id: Int) {
        myBpDatabaseQueries.DeleteBp(id.toLong())
    }

    fun selectBp(id: Int) : Bp? {
        return myBpDatabaseQueries.SelectBpById(id.toLong()).executeAsOneOrNull()
    }

    fun deleteAll() {
        myBpDatabaseQueries.DeleteAllBp()
    }

    fun selectAll() : List<Bp>? {
        val bpList = myBpDatabaseQueries.SelectAll().executeAsList()
        return bpList.ifEmpty { null }
    }

    fun dropBpTable() {
        myBpDatabaseQueries.DropBpTable()
    }
}