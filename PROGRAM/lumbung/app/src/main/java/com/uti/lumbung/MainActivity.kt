package com.uti.lumbung

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.view.View


class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: dbData
    private lateinit var database: SQLiteDatabase
    private lateinit var spinnerTanaman: Spinner
    private lateinit var txNama: TextView
    private lateinit var txKapasitas: TextView
    private lateinit var txIsi: TextView
    private lateinit var txHasil: EditText
    private lateinit var tambahButton: Button
    private lateinit var kosongButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = dbData(this)
        database = dbHelper.writableDatabase

        spinnerTanaman = findViewById(R.id.spinnerTanaman)
        txNama = findViewById(R.id.txNama)
        txKapasitas = findViewById(R.id.txKapasitas)
        txIsi = findViewById(R.id.txIsi)
        txHasil = findViewById(R.id.txHasil)
        tambahButton = findViewById(R.id.tambah)
        kosongButton = findViewById(R.id.kosong)

        val tanamanList = arrayOf("Padi", "Jagung", "Singkong")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tanamanList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTanaman.adapter = adapter

        spinnerTanaman.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedTanaman = parent.getItemAtPosition(position).toString()
                loadTanamanData(selectedTanaman)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        tambahButton.setOnClickListener {
            val hasilTambah = txHasil.text.toString().toIntOrNull()
            hasilTambah?.let {
                tambahIsiLumbung(it)
            }
        }

        kosongButton.setOnClickListener {
            kosongkanLumbung()
        }
    }

    private fun loadTanamanData(tanaman: String) {
        val cursor = database.query(
            "tanaman", arrayOf("nama", "kapasitas", "isi"),
            "nama=?", arrayOf(tanaman),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"))
            val kapasitas = cursor.getInt(cursor.getColumnIndexOrThrow("kapasitas"))
            val isi = cursor.getInt(cursor.getColumnIndexOrThrow("isi"))

            txNama.text = nama
            txKapasitas.text = if (isi >= kapasitas) "Penuh" else "$kapasitas kg"
            txIsi.text = "$isi kg"
        }
        cursor.close()
    }

    private fun tambahIsiLumbung(jumlah: Int) {
        val tanaman = spinnerTanaman.selectedItem.toString()
        val cursor = database.query(
            "tanaman", arrayOf("kapasitas", "isi"),
            "nama=?", arrayOf(tanaman),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val kapasitas = cursor.getInt(cursor.getColumnIndexOrThrow("kapasitas"))
            var isi = cursor.getInt(cursor.getColumnIndexOrThrow("isi"))

            isi += jumlah
            if (isi > kapasitas) isi = kapasitas

            val values = ContentValues().apply {
                put("isi", isi)
            }
            database.update("tanaman", values, "nama=?", arrayOf(tanaman))

            txKapasitas.text = if (isi >= kapasitas) "Penuh" else "$kapasitas kg"
            txIsi.text = "$isi kg"
        }
        cursor.close()

        // Mengosongkan teks di EditText setelah penambahan
        txHasil.text.clear()
    }


    private fun kosongkanLumbung() {
        val tanaman = spinnerTanaman.selectedItem.toString()
        val values = ContentValues().apply {
            put("isi", 0)
        }
        database.update("tanaman", values, "nama=?", arrayOf(tanaman))

        txKapasitas.text = "4000 kg"
        txIsi.text = "0 kg"
    }
}
