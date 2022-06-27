package id.co.rizki.simpleroomormapp

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.co.rizki.simpleroomormapp.databinding.ItemStudentBinding
import id.co.rizki.simpleroomormapp.entity.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * Created by Rizky Putra on 22/06/22.
 */

//step 3 implement recyclerview, bikin adapter
class ListAdapter(private val listStudent: List<Student>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student, item: Student) {

            //step 4 implement recyclerview, bind data ke view
            binding.tvId.text = "Id User : ${student.id}"
            binding.tvName.text = "Nama User : ${student.nama}"
            binding.tvEmail.text = "Email User : ${student.email}"

            binding.ivDelete.setOnClickListener {

                val database = StudentDatabase.getInstance(it.context as MainActivity)

                AlertDialog.Builder(binding.root.context as MainActivity)
                    .setPositiveButton("Ya") { p0, p1 ->

                        GlobalScope.launch {

                            val result = database?.studentDao()?.deleteStudent(item)

                            (binding.root.context as MainActivity).runOnUiThread {
                                if (result != 0) {
                                    Toast.makeText(
                                        it.context, "Data ${item.nama} berhasil dihapus",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        it.context, "Data ${item.nama} Gagal dihapus",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                            (binding.root.context as MainActivity).fetchData()
                        }
                    }.setNegativeButton("Tidak") { p0, p1 ->
                        p0.dismiss()
                    }.setMessage("Apakah Anda Yakin ingin menghapus data ${item.nama}")
                    .setTitle("Konfirmasi Hapus").create()
                    .show()


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Student = listStudent[position]
        holder.bind(item, item)
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }
}