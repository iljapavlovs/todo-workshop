package lv.dt.todoworkshop.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_todo_list_item.view.*
import lv.dt.todoworkshop.Note
import lv.dt.todoworkshop.R

/*
 * For more information on adapters you can read:
 * https://developer.android.com/guide/topics/ui/layout/recyclerview.html
 * https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.html
 */
class NoteListAdapter(
    private val context: Context,
    private val onClickCallback: (Long) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<NoteViewHolder>() {

    private var notes = emptyList<Note>()

    fun set(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        LayoutInflater
            .from(context)
            .inflate(R.layout.view_todo_list_item, parent, false)
            .let { NoteViewHolder(it) }
            .also { }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], onClickCallback, getColor(position, context))
    }

    override fun getItemCount(): Int = notes.size

    private fun getColor(position: Int, context: Context): Int {
        val r = context.resources
        val colors = listOf(
            r.getColor(R.color.pink),
            r.getColor(R.color.orange),
            r.getColor(R.color.yellow),
            r.getColor(R.color.green),
            r.getColor(R.color.blue),
            r.getColor(R.color.purple)
        )
        return colors[position % colors.size]
    }
}

class NoteViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    fun bind(item: Note, onClick: (Long) -> Unit, color: Int) {
        itemView.date.text = item.date
        itemView.title.text = item.title
        itemView.todo.text = item.note
        itemView.notification_background.setBackgroundColor(color)
        itemView.card.setOnClickListener { onClick(item.id) }
    }
}