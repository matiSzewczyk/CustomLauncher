package com.matis.customlauncher.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.model.PageLayoutDto
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
    var checkedState by remember {
        mutableStateOf(uiState.layoutDialogToDisplay?.getLayoutType(uiState))
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                LayoutList(
                    dialogType = uiState.layoutDialogToDisplay!!,
                    checkedState = checkedState,
                    onCheckedChange = { checkedState = it })
                ButtonSection(
                    onCancelClicked = { onDismissRequest() },
                    onConfirmClicked = {
                        if (checkedState == null) coroutineScope.launch { context.showToast("No layout selected") }
                        else onConfirmClicked(
                            PageLayoutDto(
                                page = uiState.layoutDialogToDisplay.page,
                                layoutType = checkedState!!
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun LayoutList(
    dialogType: LayoutDialogType,
    checkedState: LayoutType?,
    onCheckedChange: (LayoutType) -> Unit
) {
    dialogType.supportedLayouts.forEach {
        LayoutItem(
            layoutType = it,
            checked = it == checkedState,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}

@Composable
fun LayoutItem(
    layoutType: LayoutType,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() }
        )
        Text(
            text = layoutType.rawName,
            modifier = Modifier.padding()
        )
    }
}

@Composable
fun ButtonSection(
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = { onCancelClicked() }) {
            Text(text = "Cancel")
        }
        TextButton(onClick = { onConfirmClicked() }) {
            Text(text = "Confirm")
        }
    }
}

private fun LayoutDialogType.getLayoutType(uiState: UiState): LayoutType = when (this) {
    LayoutDialogType.Home -> uiState.appliedLayoutTypeForHome.layoutType
    LayoutDialogType.AppDrawer -> uiState.appliedLayoutTypeForAppDrawer.layoutType
}

