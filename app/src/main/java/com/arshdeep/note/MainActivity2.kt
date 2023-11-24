package com.arshdeep.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {

    private  lateinit var txtitle: TextView
    private lateinit var txtnote: TextView
    private lateinit var btnSubmit:Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        txtitle = findViewById(R.id.editTextText)
        txtnote = findViewById(R.id.editText2)
        btnSubmit = findViewById(R.id.button)

        dbRef = FirebaseDatabase.getInstance().getReference("Data")
        btnSubmit.setOnClickListener {
            saveNotes()
            Log.e("WHAT","SUPPOSE TO BE SENT")
        }



    }

    private fun saveNotes() {
        val title = txtitle.text.toString()
        val note = txtnote.text.toString()
        val noteId = dbRef.push().key!!
        var notes = NotesModel(noteId, title, note)

        if (title.isEmpty()||note.isEmpty()) {
            Toast.makeText(this, "Please enter valid text", Toast.LENGTH_LONG).show()
        } else{
            dbRef.child(noteId).setValue(notes)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data Saved correctly", Toast.LENGTH_LONG).show()

                }
                .addOnCanceledListener {
                    Toast.makeText(this, "Data Saves", Toast.LENGTH_LONG).show()
                    txtitle.text = ""
                    txtnote.text = ""
                    finish()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}


