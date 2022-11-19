package com.cafeaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cafeaapp.ui.screens.main.MainScreen
import com.cafeaapp.ui.theme.CafeAppTheme

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