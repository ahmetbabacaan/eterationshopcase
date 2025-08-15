package com.babacan.eterationcase.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.domain.model.ShopProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDAO {

    @Query("""
        INSERT INTO shop_products (
            id, name, price, image, description, brand, model, createdAt, quantity, isFavorite
        )
        VALUES (
            :id, :name, :price, :image, :description, :brand, :model, :createdAt, 1, :isFavorite
        )
        ON CONFLICT(id) DO UPDATE SET quantity = quantity + 1
    """)
    suspend fun insertOrIncrementQuantity(
        id: String,
        name: String,
        price: String,
        image: String,
        description: String,
        brand: String,
        model: String,
        createdAt: String,
        isFavorite: Boolean
    )

    @Query("UPDATE shop_products SET quantity = quantity - 1 WHERE id = :id")
    suspend fun decrementQuantity(id: String)

    @Query("DELETE FROM shop_products WHERE quantity <= 0 AND id = :id")
    suspend fun deleteIfZero(id: String)

    @Transaction
    suspend fun decreaseQuantity(id: String) {
        decrementQuantity(id)
        deleteIfZero(id)
    }

    @Query("SELECT * FROM shop_products")
    fun getAllCartItems(): Flow<List<ShopProduct>>

    @Query("DELETE FROM shop_products")
    suspend fun clearCart()


    @Query("SELECT COUNT(*) FROM shop_products")
    fun getDistinctItemCountFlow(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite): Long

    @Query("DELETE FROM shop_favorites WHERE id = :productId")
    suspend fun delete(productId: String)

    @Transaction
    suspend fun toggleFavorite(favorite: Favorite) {
        val inserted = insert(favorite)
        if (inserted == -1L) {
            delete(favorite.id)
        }
    }

    @Query("SELECT * FROM shop_favorites")
    fun getAllFavorites(): Flow<List<Favorite>>
}

