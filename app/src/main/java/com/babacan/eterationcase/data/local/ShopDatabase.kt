package com.babacan.eterationcase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.domain.model.ShopProduct

@Database(entities = [ShopProduct::class, Favorite::class], version = 1, exportSchema = false)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun shopDao(): ShopDAO
}