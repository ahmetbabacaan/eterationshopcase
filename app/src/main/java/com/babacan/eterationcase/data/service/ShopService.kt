package com.babacan.eterationcase.data.service

import com.babacan.eterationcase.data.model.ShopProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopService {
    @GET("products")
    suspend fun getProducts(): List<ShopProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): ShopProductDto
}