package com.matis.customlauncher.ui.shared

import android.content.Context
import android.graphics.drawable.Drawable

fun Context.getApplicationIcon(packageName: String): Drawable? =
    runCatching { packageManager.getApplicationIcon(packageName) }
        .getOrNull()
