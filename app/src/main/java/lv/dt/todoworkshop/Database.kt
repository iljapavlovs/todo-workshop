package lv.dt.todoworkshop

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

/*
 * For more information on room database you can read:
 * https://developer.android.com/jetpack/androidx/releases/room
 * https://developer.android.com/topic/libraries/architecture/room
 */

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun notesDao(): NotesDao

}

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "uid") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "date") var date: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "note") var note: String = ""
)

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE uid = :noteId")
    fun getNote(noteId: Long): LiveData<Note>

    @Insert
    suspend fun saveNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
