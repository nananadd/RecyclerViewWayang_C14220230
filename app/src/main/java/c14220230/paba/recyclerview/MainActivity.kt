package c14220230.paba.recyclerview

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        _rvWayang = findViewById<RecyclerView>(R.id.rvWayang)
        SiapkanData()
        TambahData()
        TampilkanData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun SiapkanData() {
//        _nama = resources.getStringArray(R.array.namaWayang)
//        _deskripsi = resources.getStringArray(R.array.deskripsiWayang)
//        _karakter = resources.getStringArray(R.array.karakterUtamaWayang)
//        _gambar = resources.getStringArray(R.array.gambarWayang)

        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()
    }

    fun TambahData() {
        arWayang.clear()
        for (position in _nama.indices) {
            val data = wayang(
                _gambar[position],
                _nama[position],
                _karakter[position],
                _deskripsi[position]
            )
            arWayang.add(data)
        }
    }

    fun TampilkanData() {
//        _rvWayang.layoutManager = LinearLayoutManager(this)
//        _rvWayang.layoutManager = GridLayoutManager(this,2)
//        _rvWayang.layoutManager = StaggeredGridLayoutManager(
//            2,
//            LinearLayoutManager.VERTICAL)
        _rvWayang.adapter = adapterRecView(arWayang)
        val adapterWayang = adapterRecView(arWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickback(object : adapterRecView.OnItemClickback {
            override fun onItemClicked(data: wayang) {
                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(
                    this@MainActivity, detWayang::class
                        .java
                )
                intent.putExtra("kirimData", data)
                startActivity(intent)

            }

            override fun delData(Pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah Benar Data "+_nama[Pos]+" Akan Dihapus?")
                    .setPositiveButton(
                        "HAPUS",
                        DialogInterface.OnClickListener { dialog, which ->
                            _nama.removeAt(Pos)
                            _karakter.removeAt(Pos)
                            _deskripsi.removeAt(Pos)
                            _gambar.removeAt(Pos)
                            TambahData()
                            TampilkanData()
                        }
                    )
                    .setNegativeButton(
                        "BATAL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Data Batal Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ).show()
            }
        })
    }
}

//private lateinit var _nama : Array<String>
//private lateinit var _karakter : Array<String>
//private lateinit var _deskripsi : Array<String>
//private lateinit var _gambar : Array<String>

private lateinit var _nama : MutableList<String>
private lateinit var _karakter : MutableList<String>
private lateinit var _deskripsi : MutableList<String>
private lateinit var _gambar : MutableList<String>

private var arWayang = arrayListOf<wayang>()

private lateinit var _rvWayang : RecyclerView
