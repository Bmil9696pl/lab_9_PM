package com.example.lab9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var students: MutableList<StudentModel>,
    private val onEditClickListener: (StudentModel) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun addStudent(student: StudentModel) {
        students.add(student)
        notifyItemInserted(students.size - 1)
    }

    fun updateStudent(updatedStudent: StudentModel) {
        val position = students.indexOfFirst { it.id == updatedStudent.id }
        if (position != -1) {
            students[position] = updatedStudent
            notifyItemChanged(position)
        }
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        init {
            itemView.findViewById<View>(R.id.editButton).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onEditClickListener(students[position])
                }
            }
        }

        fun bind(student: StudentModel) {
            nameTextView.text = student.name
        }
    }
}
