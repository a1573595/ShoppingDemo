package com.a1573595.shoppingdemo.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a1573595.shoppingdemo.MainApplication

private const val DB_NAME = "SampleDatabase.db"
private const val DB_Version = 1
const val TABLE_CART = "Table_Cart"

@Database(entities = [Cart::class], version = DB_Version)
abstract class DBHelper : RoomDatabase() {
    companion object {
        val instance: DBHelper by lazy { build() }

        @Synchronized
        fun build(): DBHelper {
            return Room.databaseBuilder(
                MainApplication.appContext,
                DBHelper::class.java,
                DB_NAME
            )
//            .allowMainThreadQueries() // 可在MainThread執行
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getCartDao(): CartDao
}