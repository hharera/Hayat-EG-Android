package com.whiteside.dwaa.network.firebase

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.whiteside.dwaa.common.DatabasePaths
import com.whiteside.dwaa.model.MedicineData
import com.whiteside.dwaa.network.repository.MedicineRepository
import com.whiteside.dwaa.ui.feed.FeedMedicine
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class FirebaseMedicineRepository @Inject constructor(
    val firestore: FirebaseFirestore,
    val firebaseStorage: FirebaseStorage
) : MedicineRepository {

    override fun addMedicine(medicineData: MedicineData) =
        firestore
            .collection(DatabasePaths.medicines)
            .document(medicineData.id)
            .set(medicineData)

    override fun getNewMedicineId(medicineCategory: String) =
        firestore
            .collection(medicineCategory)
            .document()
            .id

    override fun uploadMedicineImage(
        image: Bitmap,
        medicineCategory: String,
        medicineId: String
    ): UploadTask {
        val inputStream = ByteArrayOutputStream()
        image.compress(CompressFormat.PNG, 0, inputStream)

        val task = firebaseStorage
            .reference
            .child(medicineCategory)
            .child(medicineId)
            .putBytes(inputStream.toByteArray())

        return task
    }

    override fun getMedicines(limit: Int, underPrice: Float): Task<QuerySnapshot> =
        firestore
            .collection(DatabasePaths.medicines)
            .limit(limit.toLong())
            .whereLessThan(FeedMedicine::price.name, underPrice)
            .get()
}