package com.example.lab9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var studentDAO: StudentDAO
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentDAO = StudentDAO(this)
        studentDAO.open()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter(studentDAO.getAllStudents() as MutableList<StudentModel>) { student ->
            showEditDialog(student)
        }
        recyclerView.adapter = adapter

        // Dodanie nowego studenta
        findViewById<Button>(R.id.button).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_add_student, null)

        val nameEditText: EditText = view.findViewById(R.id.editTextName2)
        val ageEditText: EditText = view.findViewById(R.id.editTextAge2)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Dodaj Studenta")
            .setView(view)
            .setPositiveButton("Dodaj") { _, _ ->
                val name = nameEditText.text.toString()
                val age = ageEditText.text.toString().toIntOrNull() ?: 0

                val newStudent = StudentModel(name = name, age = age)
                val newStudentId = studentDAO.insertStudent(newStudent)

                if (newStudentId != -1L) {
                    adapter.addStudent(newStudent.copy(id = newStudentId.toInt()))
                }
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun showEditDialog(student: StudentModel) {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_add_student, null)

        val nameEditText: EditText = view.findViewById(R.id.editTextName2)
        val ageEditText: EditText = view.findViewById(R.id.editTextAge2)

        nameEditText.setText(student.name)
        ageEditText.setText(student.age.toString())

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Edytuj Studenta")
            .setView(view)
            .setPositiveButton("Zapisz") { _, _ ->
                val name = nameEditText.text.toString()
                val age = ageEditText.text.toString().toIntOrNull() ?: 0

                val updatedStudent = student.copy(name = name, age = age)
                studentDAO.updateStudent(updatedStudent)

                adapter.updateStudent(updatedStudent)
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    override fun onDestroy() {
        studentDAO.close()
        super.onDestroy()
    }
}