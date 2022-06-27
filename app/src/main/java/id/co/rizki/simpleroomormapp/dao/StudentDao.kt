package id.co.rizki.simpleroomormapp.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import id.co.rizki.simpleroomormapp.entity.Student


/**
 * Created by Rizky Putra on 27/06/22.
 */
@Dao
interface StudentDao {

    @Query("SELECT * from Student")
    fun getAllStudent() : List<Student>

    @Insert(onConflict = REPLACE)
    fun insertStudent(student: Student) : Long

    @Update
    fun updateStudent(student: Student) : Int

    @Delete
    fun deleteStudent(student: Student) : Int

}