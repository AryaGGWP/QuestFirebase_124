package com.example.pertemuan15.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan15.MahasiswaApp
import com.example.pertemuan15.ui.home.viewmodel.HomeViewModel
import com.example.pertemuan15.ui.insert.viewmodel.InsertViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(mahasiswaApp().container.repositoryMhs) }
        initializer { InsertViewModel(mahasiswaApp().container.repositoryMhs) }
    }
}

fun CreationExtras.mahasiswaApp(): MahasiswaApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApp)