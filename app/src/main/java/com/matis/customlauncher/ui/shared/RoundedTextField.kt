package com.matis.customlauncher.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun RoundedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldDisplayIcon by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(
                brush = SolidColor(MaterialTheme.colorScheme.background.copy(alpha = .8f)),
                shape = RoundedCornerShape(32.dp)
            )
            .border(
                width = 2.dp,
                brush = SolidColor(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        Icon(
            modifier = Modifier.padding(12.dp),
            imageVector = Icons.Rounded.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        Box {
            BasicTextField(
                value = text,
                onValueChange = {
                    onValueChange(it)
                    shouldDisplayIcon = it.isNotEmpty()
                },
                cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
                modifier = modifier,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
        }
    }
}
