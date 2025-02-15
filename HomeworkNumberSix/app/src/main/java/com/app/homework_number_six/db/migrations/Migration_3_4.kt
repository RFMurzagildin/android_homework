package com.app.homework_number_six.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.homework_number_six.db.InceptionDatabase

class Migration_3_4 : Migration(3, 4 ) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("ALTER TABLE 'users' ADD COLUMN 'confirm_password' TEXT NOT NULL")
        }catch (ex: Exception){
            Log.e(InceptionDatabase.DB_LOG_KEY, "Error while 3_4 migration: ${ex.message}")
        }
    }
}