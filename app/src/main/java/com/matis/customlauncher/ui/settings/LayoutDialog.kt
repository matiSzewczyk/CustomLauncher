package com.matis.customlauncher.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.matis.customlauncher.model.domain.HomePageLayoutType
import com.matis.customlauncher.model.domain.PageLayoutDto
import com.matis.customlauncher.ui.main.showToast
import com.matis.customlauncher.ui.settings.data.model.LayoutDialogType
import com.matis.customlauncher.ui.settings.data.model.UiState
import kotlinx.coroutines.launch

@Composable
fun LayoutDialog(
    uiState: UiState,
    onDismissRequest: () -> Unit,
    onConfirmClicked: (PageLayoutDto) -> Unit
) {
    var selectedState by remember {
        mutableStateOf(uiState.layoutDialogToDisplay?.getLayoutType(uiState))
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TitleSection()
                LayoutList(
                    dialogType = uiState.layoutDialogToDisplay!!,
                    selected = selectedState,
                    onClicked = { selectedState = it }
                )
                ButtonSection(
                    onCancelClicked = { onDismissRequest() },
                    onConfirmClicked = {
                        if (selectedState == null) coroutineScope.launch { context.showToast("No layout selected") }
                        else onConfirmClicked(
                            PageLayoutDto(
                                page = uiState.layoutDialogToDisplay.page,
                                layoutType = selectedState!!
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Select your preferred layout",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun LayoutList(
    dialogType: LayoutDialogType,
    selected: HomePageLayoutType?,
    onClicked: (HomePageLayoutType) -> Unit
) {
    Column {
        dialogType.supportedLayouts.forEach {
            LayoutItem(
                layoutType = it,
                selected = it == selected,
                onClicked = { onClicked(it) }
            )
        }
    }
}

@Composable
fun LayoutItem(
    layoutType: HomePageLayoutType,
    selected: Boolean,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClicked() }
        )
        Text(text = layoutType.rawName)
    }
}

@Composable
fun ButtonSection(
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { onCancelClicked() },
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(text = "Cancel")
        }
        TextButton(
            onClick = { onConfirmClicked() },
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(text = "Confirm")
        }
    }
}

private fun LayoutDialogType.getLayoutType(uiState: UiState): HomePageLayoutType =
    when (this) {
        LayoutDialogType.Home -> uiState.appliedLayoutTypeForHome.layoutType
        LayoutDialogType.AppDrawer -> uiState.appliedLayoutTypeForAppDrawer.layoutType
    }

