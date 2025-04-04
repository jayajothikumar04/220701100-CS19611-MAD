package com.example.exp5_100

import android.provider.BaseColumns

object DBContract {
    object StudentEntry : BaseColumns {
        const val TABLE_NAME = "students"
        const val COLUMN_REGISTER = "register_number"
        const val COLUMN_NAME = "name"
        const val COLUMN_CGPA = "cgpa"
    }
}
