package id.co.rizki.simpleroomormapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.rizki.simpleroomormapp.databinding.ActivityMainBinding
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

        binding.rvStudent.layoutManager = LinearLayoutManager(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.cpiLoading.show()

        fetchData()
    }

    fun fetchData(){
        val handler = Handler()
        handler.postDelayed({

            binding.cpiLoading.hide()

            GlobalScope.launch {
                val listStudent = dataBase?.studentDao()?.getAllStudent()

                runOnUiThread{
                    listStudent?.let {
                        val adapter = ListAdapter(it)
                        binding.rvStudent.adapter = adapter
                    }
                }
            }
        }, 2000)

    }

}