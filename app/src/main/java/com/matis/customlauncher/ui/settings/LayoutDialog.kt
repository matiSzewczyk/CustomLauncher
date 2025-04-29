package com.matis.customlauncher.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.matis.customlauncher.model.LayoutType
import com.matis.customlauncher.ui.settings.data.model.LayoutDialogType

@Composable
fun LayoutDialog(
    dialogType: LayoutDialogType,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                LayoutList(dialogType = dialogType)
                ButtonSection(
                    onCancelClicked = { onDismissRequest() },
                    onConfirmClicked = { }
                )
            }
        }
    }
}

@Composable
fun LayoutList(dialogType: LayoutDialogType) {
    var checkedState by remember { mutableStateOf<LayoutType?>(null) }

    dialogType.supportedLayouts.forEach {
        LayoutItem(
            layoutType = it,
            checked = it == checkedState,
            onCheckedChange = { checkedState = it }
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
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        TextButton(onClick = { onCancelClicked() }) {
            Text(text = "Cancel")
        }
        TextButton(onClick = { onConfirmClicked() }) {
            Text(text = "Confirm")
        }
    }
}

