package org.imaginativeworld.simplemvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.imaginativeworld.simplemvvm.models.DemoUserEntity

// Help: https://android.jlelse.eu/android-room-using-kotlin-f6cc0a05bf23

@Database(entities = [DemoUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    "DROP TABLE tbl_user"
//                )
//            }
//        }

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyDatabase.db"
        )
//            .addMigrations(MIGRATION_1_2) // Note: Migration example
            .fallbackToDestructiveMigration() // Note: Mostly for debug
            .build()

    }

}