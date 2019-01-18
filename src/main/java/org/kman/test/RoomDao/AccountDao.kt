package org.kman.test.RoomDao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {

    @Query("SELECT * from Accounts ORDER BY login DESC")
    fun getAll(): LiveData<List<Account>>

    @Insert
    fun insert(account: Account): Long

    @Update
    fun update(account: Account)

    @Query("UPDATE Accounts SET flags=:flags WHERE _id = :id")
    fun setFlags(id: Long, flags: Int)

    @Query("SELECT flags FROM Accounts WHERE _id = :id")
    fun getFlags(id: Long): Int

    @Query("DELETE FROM Accounts WHERE _id = :id")
    fun deleteByDbId(id: Long)

    @Query("DELETE FROM Accounts")
    fun deleteAll()

    fun setIsDisabled(id: Long, isDisabled: Boolean) {
        if (isDisabled) {
            setFlagsImpl(id, Account.FLAG_DISABLED, 0)
        } else {
            setFlagsImpl(id, 0, Account.FLAG_DISABLED)
        }
    }

    @Transaction
    fun setFlagsImpl(id: Long, set: Int, clear: Int) {
        val flagsOld = getFlags(id)
        val flagsNew = (flagsOld or set) and clear.inv()

        if (flagsOld != flagsNew) {
            setFlags(id, flagsNew)
        }
    }
}
