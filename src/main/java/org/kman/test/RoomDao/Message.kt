package org.kman.test.RoomDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Messages")
class Message(
    @PrimaryKey(autoGenerate = true) val _id: Long,

    @ColumnInfo(name = "subject") val subject: String?,
    @ColumnInfo(name = "when_date") val when_date: Long,

    @ColumnInfo(name = "flags") var flags: Int
) {

    fun isStarred() = (flags and FLAG_STARRED) != 0
    fun isUnread() = (flags and FLAG_UNREAD) == 0

    companion object {
        val FLAG_UNREAD = 0x0001
        val FLAG_STARRED = 1 shl 1
    }
}
