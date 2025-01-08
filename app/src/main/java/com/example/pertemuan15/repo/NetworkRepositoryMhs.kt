package com.example.pertemuan15.repo

import com.example.pertemuan15.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkRepositoryMhs (
    private val firestore: FirebaseFirestore
):RepositoryMhs{
    override suspend fun  insertMhs(mahasiswa: Mahasiswa){
        firestore.collection("Mahasiswa").add(mahasiswa)
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("", Query.Direction.ASCENDING)
            .addSnapshotListener{value, error ->
                if (value != null){
                    val mhslist = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)
                    }
                    trySend(mhslist)
                }
            }
        awaitClose {
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> {
        TODO()
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) { //menghapus data mahasiswa

    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) { //memperbarui data mahasiswa dalam database

    }
}