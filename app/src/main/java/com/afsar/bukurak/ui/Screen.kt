package com.afsar.bukurak.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object List : Screen("list")
}

