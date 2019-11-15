package lv.dt.todoworkshop.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lv.dt.todoworkshop.App
import lv.dt.todoworkshop.Note

/*
 * ViewModel is used to store data in case activity is paused or recreated.
 *
 * https://developer.android.com/topic/libraries/architecture/viewmodel.html
 */
class FormViewModel : ViewModel() {

    private val vmJob = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

    fun saveToDatabase(note: Note) {
        vmScope.launch(Dispatchers.IO) {
            App.NOTES.saveNote(note)
        }
    }

    fun updateInDatabase(note: Note) {
        vmScope.launch(Dispatchers.IO) {
            App.NOTES.updateNote(note)
        }
    }

    fun deleteFromDatabase(note: Note) {
        vmScope.launch(Dispatchers.IO) {
            App.NOTES.deleteNote(note)
        }
    }

    override fun onCleared() {
        vmJob.complete()
    }
}