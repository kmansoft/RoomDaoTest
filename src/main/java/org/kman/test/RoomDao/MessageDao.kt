package org.kman.test.RoomDao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MessageDao {

    @Query("SELECT * from Messages ORDER BY when_date DESC")
    fun getAll(): LiveData<List<Message>>

    @Insert
    fun insert(message: Message): Long

    @Update
    fun update(message: Message)

    @Query("UPDATE Messages SET flags=:flags WHERE _id = :id")
    fun setFlags(id: Long, flags: Int)

    @Query("SELECT flags FROM Messages WHERE _id = :id")
    fun getFlags(id: Long): Int

    @Query("DELETE FROM Messages WHERE _id = :id")
    fun deleteByDbId(id: Long)

    @Query("DELETE FROM Messages")
    fun deleteAll()

    fun setIsUnread(id: Long, isUnread: Boolean) {
        val flagsOld = getFlags(id)
        val flagsNew = if (isUnread) {
            flagsOld and Message.FLAG_SEEN.inv()
        } else {
            flagsOld or Message.FLAG_SEEN
        }

        if (flagsOld != flagsNew) {
            setFlags(id, flagsNew)
        }
    }

    @Transaction
    fun setIsStarred(id: Long, isStarred: Boolean) {
        val flagsOld = getFlags(id)
        val flagsNew = if (isStarred) {
            flagsOld or Message.FLAG_STARRED
        } else {
            flagsOld and Message.FLAG_STARRED.inv()
        }

        if (flagsOld != flagsNew) {
            setFlags(id, flagsNew)
        }
    }
}
