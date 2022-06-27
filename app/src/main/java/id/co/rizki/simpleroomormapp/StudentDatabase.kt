package id.co.rizki.simpleroomormapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.co.rizki.simpleroomormapp.dao.StudentDao
import id.co.rizki.simpleroomormapp.entity.Student


/**
 * Created by Rizky Putra on 27/06/22.
 */
@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao() :  StudentDao

    companion object {

        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase? {
            if(INSTANCE == null) {
                synchronized(StudentDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    StudentDatabase::class.java, "Student.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}