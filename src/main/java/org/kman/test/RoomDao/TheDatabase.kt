package org.kman.test.RoomDao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Account::class, Message::class], version = 1)
public abstract class TheDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun messageDao(): MessageDao
}
