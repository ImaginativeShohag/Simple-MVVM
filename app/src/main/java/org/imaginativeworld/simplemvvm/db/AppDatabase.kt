package org.imaginativeworld.simplemvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date
import org.imaginativeworld.simplemvvm.models.DemoUserEntity
import org.imaginativeworld.simplemvvm.models.awesometodos.TodoEntity

// Help: https://android.jlelse.eu/android-room-using-kotlin-f6cc0a05bf23

@Database(
    entities = [
        DemoUserEntity::class,
        TodoEntity::class
    ],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao

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

class DateConverter {
    @TypeConverter
    fun toDatabaseValue(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromDatabaseValue(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}
