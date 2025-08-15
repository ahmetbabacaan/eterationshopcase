package com.babacan.eterationcase.domain.model

import androidx.room.Entity

@Entity(tableName = "shop_products")
data class ShopProduct(
    @androidx.room.PrimaryKey
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val brand: String,
    val model: String,
    val createdAt: String,
    val quantity: Int = 0,
    val isFavorite: Boolean = false,
)

@Entity(tableName = "shop_favorites")
data class Favorite(
    @androidx.room.PrimaryKey
    val id: String,
    val image: String,
    val name: String,
    val price: String,
)
