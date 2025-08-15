package com.babacan.eterationcase.data.model

import com.babacan.eterationcase.domain.model.ShopProduct

data class ShopProductDto(
    val id: String,
    val name: String,
    val image: String,
    val price: String,
    val description: String,
    val model: String,
    val brand: String,
    val createdAt: String
)

fun ShopProductDto.toDomain() = ShopProduct(
    id = id,
    name = name,
    price = price + "₺",
    image = image,
    description = description,
    brand = brand,
    model = model,
    createdAt = createdAt
)
