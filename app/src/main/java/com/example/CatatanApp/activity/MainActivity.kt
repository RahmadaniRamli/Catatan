package com.example.CatatanApp.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.CatatanApp.R
import com.example.CatatanApp.room.Constant
import com.example.CatatanApp.room.Note
import com.example.CatatanApp.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { NoteDB(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadNote()
    }

    fun loadNote() {
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d("MainActivity", "dbResponse: $notes")
            withContext(Dispatchers.Main) {
                noteAdapter.setData(notes)
            }
        }
    }

    //Edit
    fun setupListener() {
        button_create.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener {
            //Read
            override fun onClick(note: Note) {
                Toast.makeText(applicationContext, note.title, Toast.LENGTH_SHORT).show()
                intentEdit(note.id, Constant.TYPE_READ)
            }

            //Update
            override fun onUpdate(note: Note) {
                intentEdit(note.id, Constant.TYPE_UPDATE)
            }

            //Delete
            override fun onDelete(note: Note) {
                deleteDialog(note)
            }

        })
        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }

    //AlertDialog untuk konfirmasi hapus
    private fun deleteDialog(note: Note) {
        val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${note.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    loadNote()
                }
            }
        }
        alertDialog.show()
    }

}