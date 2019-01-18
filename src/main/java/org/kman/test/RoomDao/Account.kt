package org.kman.test.RoomDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Accounts")
class Account(
    @PrimaryKey(autoGenerate = true) val _id: Long,

    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String,

    @ColumnInfo(name = "flags") var flags: Int
) {

    fun isDisabled() = (flags and FLAG_DISABLED) != 0

    companion object {
        val FLAG_DISABLED = 0x0001
    }
}
