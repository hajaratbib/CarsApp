package com.example.carsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.carsapp.ui.composables.CarListScreen
import com.example.carsapp.ui.theme.CarsAppTheme
import com.example.carsapp.ui.viewModel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CarViewModel = hiltViewModel()
            val inputText by viewModel.modelValue.collectAsStateWithLifecycle()
            val isRefreshLoading by viewModel.isRefreshLoading.collectAsStateWithLifecycle()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val pullRefreshState = rememberPullRefreshState(
                refreshing = isRefreshLoading,
                onRefresh = viewModel::loadCars
            )
            CarsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CarListScreen(
                        modifier = Modifier.padding(innerPadding),
                        state,
                        inputText,
                        viewModel::fetchCars,
                        inputText.isNotBlank(),
                        viewModel::setModelValue,
                        isRefreshLoading,
                        pullRefreshState
                    )
                }
            }
        }
    }
}