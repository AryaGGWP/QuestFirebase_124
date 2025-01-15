package com.example.pertemuan15.repo

import android.util.Log
import com.example.pertemuan15.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkRepositoryMhs (
    private val firestore: FirebaseFirestore
): RepositoryMhs {
    override suspend fun insertMhs(mahasiswa: Mahasiswa){
        try {
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e: Exception) {
            throw Exception("Gagal menambahkan data mahasiswa: ${e.message}")
        }
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim", Query.Direction.ASCENDING)
            .addSnapshotListener{value, error ->
                if (value != null){
                    val mhslist = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)!!
                    }
                    trySend(mhslist)
                }
            }
        awaitClose {
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> = callbackFlow {
        val mhsDocument = firestore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val mhs = value.toObject(Mahasiswa::class.java)
                    if (mhs != null) {
                        trySend(mhs)
                    } else {
                        trySend(Mahasiswa()) // Default jika data kosong
                    }
                } else if (error != null) {
                    close(error)
                }
            }
        awaitClose()
    }


    override suspend fun deleteMhs(nim: String) {
        if (nim.isBlank()) {
            Log.e("RepositoryMhs", "NIM tidak boleh kosong.")
            throw IllegalArgumentException("NIM tidak boleh kosong.")
        }
        try {
            firestore.collection("Mahasiswa")
                .document(nim)
                .delete()
                .await()

            Log.d("RepositoryMhs", "Berhasil menghapus data mahasiswa dengan NIM: $nim")
        } catch (e: FirebaseFirestoreException) {
            Log.e("RepositoryMhs", "Kesalahan Firebase saat menghapus mahasiswa: ${e.message}")
            throw FirebaseFirestoreException(
                "Gagal menghapus data mahasiswa dari Firestore: ${e.message}",
                e.code
            )
        } catch (e: Exception) {
            Log.e("RepositoryMhs", "Kesalahan saat menghapus mahasiswa: ${e.message}")
            throw Exception("Gagal menghapus mahasiswa: ${e.message}")
        }
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa, nim: String) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        } catch (e: Exception){
            throw Exception ("Gagal mengupdate data Mahasiswa: ${e.message}")
        }
    }
}