package id.co.rizki.simpleroomormapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.rizki.simpleroomormapp.databinding.ActivityMainBinding
import id.co.rizki.simpleroomormapp.entity.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var dataBase : StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBase = StudentDatabase.getInstance(this)

        fetchData()

        binding.rvStudent.layoutManager = LinearLayoutManager(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listStudent = dataBase?.studentDao()?.getAllStudent()

            runOnUiThread{
                listStudent?.let {
                    val adapter = ListAdapter(it)
                    binding.rvStudent.adapter = adapter
                }
            }
        }
    }

}