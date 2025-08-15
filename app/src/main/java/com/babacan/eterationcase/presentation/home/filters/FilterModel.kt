package com.babacan.eterationcase.presentation.home.filters

import com.babacan.eterationcase.presentation.home.filters.SortPriorities

data class FilterModel(
    val sort: SortPriorities = SortPriorities.OLD_TO_NEW,
    val selectedModels: Set<String> = emptySet(),
    val selectedBrands: Set<String> = emptySet(),
    val brandText: String = "",
    val modelText: String = ""
)