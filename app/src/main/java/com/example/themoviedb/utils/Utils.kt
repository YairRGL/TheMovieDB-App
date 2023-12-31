package com.example.themoviedb.utils

import android.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

private fun isColorDark(color: Int): Boolean {
    val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness >= 0.5
}

data class BottomNavigationItem(
    val title: String,
    val displayTitle: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)