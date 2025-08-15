package com.babacan.eterationcase.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    viewState: HomeState,
    onDismiss: () -> Unit,
    onClear: () -> Unit,
    onApply: (SortPriorities, Set<String>, Set<String>) -> Unit
) {
    var selectedSort by remember { mutableStateOf(viewState.filterModel.sort) }
    val sortOptions = SortPriorities.entries

    var selectedBrands by remember { mutableStateOf(viewState.filterModel.selectedBrands) }
    var brandOptions by remember { mutableStateOf(viewState.filteredBrands) }

    var selectedModels by remember { mutableStateOf(viewState.filterModel.selectedModels) }
    var modelOptions by remember { mutableStateOf(viewState.filteredModels) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClear()
                    },
                tint = Color.Black
            )
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Filter",
                    style = MaterialTheme.typography.titleLarge,
                )
            }

        }

        Spacer(Modifier.height(16.dp))
        Text("Sort By", style = MaterialTheme.typography.titleMedium)
        sortOptions.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedSort == option,
                    onClick = { selectedSort = option }
                )
                Text(option.value)
            }
        }

        Divider(Modifier.padding(vertical = 8.dp))

        Text("Brand", style = MaterialTheme.typography.titleMedium)

        SearchBar(
          onSearchQuery = {
                brandOptions = if (it.isEmpty()) {
                    viewState.filteredBrands
                } else {
                    viewState.filteredBrands.filter { brand ->
                        brand.contains(it, ignoreCase = true)
                    }.toSet()
                }
          }
        )
        LazyColumn(Modifier.heightIn(min = 140.dp, max = 140.dp)) {
            items(brandOptions.toImmutableList(), key = { it }) { brand ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedBrands.contains(brand),
                        onCheckedChange = {
                            selectedBrands =
                                if (it) selectedBrands + brand else selectedBrands - brand
                        }
                    )
                    Text(brand)
                }
            }
        }

            Divider(Modifier.padding(vertical = 8.dp))

            Text("Model", style = MaterialTheme.typography.titleMedium)
            SearchBar(
                onSearchQuery = {
                    modelOptions = if (it.isEmpty()) {
                        viewState.filteredModels
                    } else {
                        viewState.filteredModels.filter { model ->
                            model.contains(it, ignoreCase = true)
                        }.toSet()
                    }
                }
            )
            LazyColumn(Modifier.heightIn(min = 140.dp, max = 140.dp)) {
                items(modelOptions.toImmutableList()) { model ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = selectedModels.contains(model),
                            onCheckedChange = {
                                selectedModels =
                                    if (it) selectedModels + model else selectedModels - model
                            }
                        )
                        Text(model)
                    }
                }
            }



        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                onApply(selectedSort, selectedBrands, selectedModels)
                onDismiss()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2979FF))
        ) {
            Text("Primary", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowFilterBottomSheet(
    viewState: HomeState,
    onDismiss: () -> Unit,
    onApply: (SortPriorities, Set<String>, Set<String>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it == SheetValue.Expanded }
    )

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        dragHandle = null,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        )
    ) {
        FilterBottomSheet(
            viewState = viewState,
            onDismiss = { onDismiss() },
            onApply = onApply,
            onClear = {
                onApply(SortPriorities.OLD_TO_NEW, emptySet(), emptySet())
                onDismiss()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterBottomSheetPreview() {
    Surface {
        FilterBottomSheet(
            viewState = HomeState(),
            onDismiss = {},
            onApply = { _, _, _ -> },
            onClear = {}
        )
    }
}
