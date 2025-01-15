package com.example.pertemuan15.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan15.model.Mahasiswa
import com.example.pertemuan15.repo.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch {
            repositoryMhs.getAllMhs()
                .onStart {
                    mhsUIState = HomeUiState.Loading
                }
                .catch {
                    mhsUIState = HomeUiState.Error(exception = it)
                }
                .collect {
                    mhsUIState = if (it.isEmpty()) {
                        HomeUiState.Error(Exception("Belum ada Data"))
                    } else {
                        HomeUiState.Success(it)
                    }
                }
        }
    }

    fun deleteMhs(nim: String) {
        viewModelScope.launch {
            try {
                repositoryMhs.deleteMhs(nim)
                getMhs() // Refresh data setelah penghapusan
            } catch (e: Exception) {
                mhsUIState = HomeUiState.Error(Exception("Gagal menghapus data: ${e.message}"))
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val data: List<Mahasiswa>) : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
}



