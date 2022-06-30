package id.co.rizki.simpleroomormapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
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

        fetchData()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }

        binding.srlStudent.setOnRefreshListener {
            fetchData()
        }

    }

//    override fun onResume() {
//        super.onResume()
//
//        binding.cpiLoading.show()
//
//        fetchData()
//    }

    private fun showSkeleton() {
        binding.rvStudent.visibility = View.GONE
        binding.skeleton.visibility = View.VISIBLE
        binding.skeleton.showSkeleton()

    }

    private fun hideSkeleton() {
        binding.rvStudent.visibility = View.VISIBLE
        binding.skeleton.visibility = View.GONE
        binding.skeleton.showOriginal()
    }

    fun fetchData(){
        if(!binding.srlStudent.isRefreshing) showSkeleton()

        val handler = Handler()
        handler.postDelayed({

            binding.cpiLoading.hide()

            binding.srlStudent.isRefreshing = false

            hideSkeleton()

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