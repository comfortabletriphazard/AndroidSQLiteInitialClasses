import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DogDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dogs.db"
        private const val DATABASE_VERSION = 1

        // Table and column names
        private const val TABLE_DOGS = "dogs"
        private const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DATE_OF_BIRTH = "date_of_birth"
        const val COLUMN_BREED = "breed"
        const val COLUMN_COLOR = "color"
        const val COLUMN_SEX = "sex"
        const val COLUMN_HOMETOWN = "hometown"

        private const val CREATE_TABLE = """
            CREATE TABLE $TABLE_DOGS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_DATE_OF_BIRTH TEXT,
                $COLUMN_BREED TEXT,
                $COLUMN_COLOR TEXT,
                $COLUMN_SEX TEXT,
                $COLUMN_HOMETOWN TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOGS")
        onCreate(db)
    }

    fun addDog(dog: Dog) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, dog.name)
            put(COLUMN_DATE_OF_BIRTH, dog.dateOfBirth)
            put(COLUMN_BREED, dog.breed)
            put(COLUMN_COLOR, dog.color)
            put(COLUMN_SEX, dog.sex)
            put(COLUMN_HOMETOWN, dog.hometown)
        }
        db.insert(TABLE_DOGS, null, values)
        db.close()
    }

    fun getAllDogs(): List<Dog> {
        val dogs = mutableListOf<Dog>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DOGS, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val dog = Dog(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    dateOfBirth = getString(getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH)),
                    breed = getString(getColumnIndexOrThrow(COLUMN_BREED)),
                    color = getString(getColumnIndexOrThrow(COLUMN_COLOR)),
                    sex = getString(getColumnIndexOrThrow(COLUMN_SEX)),
                    hometown = getString(getColumnIndexOrThrow(COLUMN_HOMETOWN))
                )
                dogs.add(dog)
            }
        }
        cursor.close()
        db.close()
        return dogs
    }
}
