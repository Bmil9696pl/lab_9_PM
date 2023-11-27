package com.example.lab9

import android.provider.BaseColumns

object DBContract {
    class StudentEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "students"
            const val ID = "id"
            const val COLUMN_NAME = "name"
            const val COLUMN_AGE = "age"
        }
    }
}