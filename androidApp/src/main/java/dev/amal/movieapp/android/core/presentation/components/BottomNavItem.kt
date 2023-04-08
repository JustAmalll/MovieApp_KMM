package dev.amal.movieapp.android.core.presentation.components

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String
)