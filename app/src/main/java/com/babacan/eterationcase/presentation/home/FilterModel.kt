package com.babacan.eterationcase.presentation.home

data class FilterModel(
    val sort: SortPriorities = SortPriorities.OLD_TO_NEW,
    val selectedModels: Set<String> = emptySet(),
    val selectedBrands: Set<String> = emptySet(),
    val brandText: String = "",
    val modelText: String = ""
)
