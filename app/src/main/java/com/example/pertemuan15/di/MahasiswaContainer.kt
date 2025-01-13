package com.example.pertemuan15.di

import com.example.pertemuan15.repo.NetworkRepositoryMhs
import com.example.pertemuan15.repo.RepositoryMhs
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val repositoryMhs: RepositoryMhs
}

class MahasiswaContainer : AppContainer {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val repositoryMhs: RepositoryMhs by lazy {
        NetworkRepositoryMhs(firestore)
    }
}