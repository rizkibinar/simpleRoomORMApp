package id.co.rizki.simpleroomormapp.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/**
 * Created by Rizky Putra on 27/06/22.
 */
@Entity
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "nama") // ganti nama kolom dari tabel
    var nama: String,
    var email: String
) : Parcelable