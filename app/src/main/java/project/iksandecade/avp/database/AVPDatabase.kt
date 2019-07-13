package project.iksandecade.avp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(), version = 1)
abstract class AVPDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AVPDatabase? = null

        fun getInstance(context: Context): AVPDatabase? {
            if (INSTANCE == null) {
                synchronized(AVPDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AVPDatabase::class.java, "AVP-Data.db").build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}