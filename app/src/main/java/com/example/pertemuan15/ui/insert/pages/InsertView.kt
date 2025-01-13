package com.example.pertemuan15.ui.insert.pages

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan15.ui.PenyediaViewModel
import com.example.pertemuan15.ui.insert.viewmodel.FormState
import com.example.pertemuan15.ui.insert.viewmodel.InsertViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        when (uiState) {
            is FormState.Success -> {
                println(
                    "InsertMhsView: uiState is FormState.Success, navigate to home " + uiState.message
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
                delay(700)
                onNavigate()

                viewModel.resetSnackBarMessage()
            }

            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }

            else -> Unit
        }
    }
}