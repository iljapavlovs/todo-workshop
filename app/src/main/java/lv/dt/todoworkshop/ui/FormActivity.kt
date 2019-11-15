package lv.dt.todoworkshop.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_form.*
import lv.dt.todoworkshop.App
import lv.dt.todoworkshop.Note
import lv.dt.todoworkshop.R
import java.util.*

class FormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var currentNote: Note? = null
    private var currentDate: String = "16.11.2019"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()

        val currentNoteId = intent?.getLongExtra(KEY_NOTE_ID, 0) ?: 0
        observeCurrentNote(currentNoteId)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                onSaveClick()
                return true
            }
            R.id.action_date -> {
                showDateDialog()
                return true
            }
            R.id.action_delete -> {
                deleteNote()
                finish()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*
        TODO: Task 3. Step 1. Save or update note.
        1. Check if currentNote is null
        2. If null, call saveNote() function
        3. Otherwise call updateNote() function
        4. After this call finish() function to close screen.
        [Cheat 3.1]
     */
    private fun onSaveClick() {
        if (currentNote == null) {
            saveNote()
        } else {
            updateNote()
        }
        finish()
    }

    /*
    TODO: Task 3. Step 2. Save note.
        1. Create a Note object.
        2. Pass currentDate as date param.
        3. Pass input_title.text.toString() as title param.
        4. Pass input_note.text.toString() as note param.
        5. Call saveToDatabase() and pass created Note as param.
        [Cheat 3.2]
    */
    private fun saveNote() {
        val newNote = Note(
            date = currentDate,
            title = input_title.text.toString(),
            note = input_note.text.toString()
        )
        saveToDatabase(newNote)
    }

    /*
    TODO: Task 3. Step 3. Update note.
        1. Create a Note object.
        2. Pass currentNote!!.id as id param.
        3. Pass currentDate as date param.
        4. Pass input_title.text.toString() as title param.
        5. Pass input_note.text.toString() as note param.
        6. Call updateInDatabase() and pass created Note as param.
        [Cheat 3.3]
    */
    private fun updateNote() {
        val newNote = Note(
            id = currentNote!!.id,
            date = currentDate,
            title = input_title.text.toString(),
            note = input_note.text.toString()
        )
        updateInDatabase(newNote)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // TODO CHALLENGE: Change the format of the displayed date
        val d = dayOfMonth.formatWithZero()
        val m = (month + 1).formatWithZero()
        currentDate = "$d.$m.$year"
        date.text = currentDate
    }

    private fun deleteNote() {
        if (currentNote != null) {
            deleteFromDatabase(currentNote!!)
        }
    }

    companion object {
        private const val KEY_NOTE_ID = "key.note_id"

        fun createIntent(context: Context, noteId: Long? = null) = Intent(context, FormActivity::class.java).apply {
            putExtra(KEY_NOTE_ID, noteId)
        }
    }









    // THIS CODE IS IRRELEVANT TO THE WORKSHOP

   private lateinit var vm: FormViewModel

    private fun setUp() {
        setContentView(R.layout.activity_form)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        vm = ViewModelProviders.of(this).get(FormViewModel::class.java)
    }

    private fun saveToDatabase(note: Note) {
        vm.saveToDatabase(note)
    }

    private fun updateInDatabase(note: Note) {
        vm.updateInDatabase(note)
    }

    private fun deleteFromDatabase(note: Note) {
        vm.deleteFromDatabase(note)
    }

    private fun observeCurrentNote(id: Long) {
        App.NOTES.getNote(id).observe(this, Observer { nullableNote ->
            currentNote = nullableNote

            nullableNote?.let { notNullNote ->
                currentDate = notNullNote.date
                input_note.setText(notNullNote.note)
                input_title.setText(notNullNote.title)
            }

            date.text = nullableNote?.date ?: currentDate
        })
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        val dialog = DatePickerDialog(
            this,
            this@FormActivity,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }
}