package org.kman.test.RoomDao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class MessageDao {

    @Query("SELECT * from Messages ORDER BY when_date DESC")
    abstract fun getAll(): LiveData<List<Message>>

    @Insert
    abstract fun insert(message: Message): Long

    @Update
    abstract fun update(message: Message)

    @Query("UPDATE Messages SET flags=:flags WHERE _id = :id")
    abstract fun setFlags(id: Long, flags: Int)

    @Query("SELECT flags FROM Messages WHERE _id = :id")
    abstract fun getFlags(id: Long): Int

    @Query("DELETE FROM Messages WHERE _id = :id")
    abstract fun deleteByDbId(id: Long)

    @Query("DELETE FROM Messages")
    abstract fun deleteAll()

    fun setIsUnread(id: Long, isUnread: Boolean) {
        if (isUnread) {
            setFlagsImpl(id, Message.FLAG_UNREAD, 0)
        } else {
            setFlagsImpl(id, 0, Message.FLAG_UNREAD)
        }
    }

    @Transaction
    open fun setFlagsImpl(id: Long, set: Int, clear: Int) {
        val flagsOld = getFlags(id)
        val flagsNew = (flagsOld or set) and clear.inv()

        if (flagsOld != flagsNew) {
            setFlags(id, flagsNew)
        }
    }
}
