package com.babacan.eterationcase.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.priceToDouble(): Double {
    return this.replace("₺", "").replace(" ", "").toDoubleOrNull() ?: 0.0
}

fun Double.toPrice(): String {
    val tlFormat = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale.ROOT))
    return tlFormat.format(this) + " ₺"
}

fun String.isoDateToTimeStamp(): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")

    val date = sdf.parse(this)
    return date?.time ?: 0L
}