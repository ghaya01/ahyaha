package com.example.ahyaha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ahyaha.ui.theme.AhyahaTheme
import com.example.ahyaha.view.MainScreen
import com.example.ahyaha.viewmodel.BloodTypeViewModel
import com.example.ahyaha.viewmodel.DonorViewModel
import com.example.ahyaha.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AhyahaApp()
        }
    }
}

@Composable
fun AhyahaApp() {
    AhyahaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val newsViewModel: NewsViewModel = viewModel()
            val donorViewModel: DonorViewModel = viewModel()
            val bloodTypeViewModel: BloodTypeViewModel = viewModel()

            MainScreen(
                donorViewModel = donorViewModel,
                NewsViewModel  = newsViewModel,
                bloodTypeViewModel = bloodTypeViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
