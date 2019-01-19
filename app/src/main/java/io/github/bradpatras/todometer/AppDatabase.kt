package io.github.bradpatras.todometer

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
internal abstract class AppDatabase : RoomDatabase()
