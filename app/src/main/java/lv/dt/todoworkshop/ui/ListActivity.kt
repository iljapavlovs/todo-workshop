package lv.dt.todoworkshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import lv.dt.todoworkshop.R

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout for this activity
        setContentView(R.layout.activity_list)
        setUp()

        /*
        TODO: Task 1. Step 1. Open form activity.
            Set a listener for the add button that will open FormActivity when it's clicked
            Pass null as the parameter because we are saving new note, not updating existing
            [Cheat 1.1]
        */
        add.setOnClickListener {
            openFormActivity(null)
        }
    }

    /*
    TODO: Task 1. Step 2. Open form activity.
        1. Create intent to open form activity. Pass "this" as the first param to
        FormActivity.createIntent() function and noteId as second.
        2. Call startActivity() method and pass created intent as param.
        [Cheat 1.2]
    */
    private fun openFormActivity(noteId: Long?) {
        val intent = FormActivity.createIntent(this, noteId)
        startActivity(intent)
    }











    // THIS CODE IS IRRELEVANT TO THE WORKSHOP

    private fun setUp() {
        // Create the adapter that will handle recycler view data
        // and pass a lambda (block of code) that will be executed
        // when an item has been clicked
        val adapter = NoteListAdapter(this) { noteId ->
            openFormActivity(noteId)
        }

        // Set recycler view to show items as list
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        // Fetch all notes from the database and update data in the adapter.
        // `getAllNotes()` returns LiveData object that observes notes data in
        // database and calls Observer if anything changes. This way our list is
        // always up to date, even if we change items in form screen.
        ViewModelProviders.of(this).get(ListViewModel::class.java)
            .getAllNotes()
            .observe(this, Observer { notes ->
                notes?.let { adapter.set(notes) }
            })
    }
}