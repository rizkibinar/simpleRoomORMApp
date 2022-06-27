package id.co.rizki.simpleroomormapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.co.rizki.simpleroomormapp.databinding.ActivityAddStudentBinding
import id.co.rizki.simpleroomormapp.entity.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddStudentActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddStudentBinding

    var dataBase : StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBase = StudentDatabase.getInstance(this)


        binding.btnSubmit.setOnClickListener {
            val nama = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()

            val tempStudent = Student(null, nama, email)

            GlobalScope.async {
                val result = dataBase?.studentDao()?.insertStudent(tempStudent)
                runOnUiThread {
                    if(result != 0.toLong()) {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Sukses menambahkan ${tempStudent.nama}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AddStudentActivity,
                            "Gagal menambahkan ${tempStudent.nama}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    finish()
                }
            }


        }


    }
}