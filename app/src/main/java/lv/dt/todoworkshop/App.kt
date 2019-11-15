package lv.dt.todoworkshop

import androidx.multidex.MultiDexApplication
import androidx.room.Room

class App : MultiDexApplication() {

    companion object {
        lateinit var NOTES: NotesDao
    }

    /*
    * This is an application lifecycle callback that is executed single time when application starts,
    * before any activity has been created.
    * https://developer.android.com/reference/android/app/Application
    */

    override fun onCreate() {
        super.onCreate()

        NOTES = Room
            .databaseBuilder(this, AppDb::class.java, "notes-db")
            .build()
            .notesDao()
    }
}