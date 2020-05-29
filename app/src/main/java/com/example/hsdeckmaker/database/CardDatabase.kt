package com.example.hsdeckmaker.database

import androidx.room.Room
import androidx.room.Database
import android.content.Context
import androidx.room.RoomDatabase
import com.example.hsdeckmaker.model.CardItem

@Database(entities = [CardItem::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        private const val DATABASE_NAME = "CARDS_DATABASE"

        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getDatabase(context: Context) : CardDatabase? {
            if (INSTANCE == null) {
                synchronized(CardDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CardDatabase::class.java, DATABASE_NAME
                        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    }
                }
            }

            return INSTANCE
        }
    }
}