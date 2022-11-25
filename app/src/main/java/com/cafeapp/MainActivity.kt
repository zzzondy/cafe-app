package com.cafeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cafeapp.ui.screens.main.MainScreen
import com.cafeapp.ui.theme.CafeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeAppTheme {
                MainScreen()
            }
        }
    }
}