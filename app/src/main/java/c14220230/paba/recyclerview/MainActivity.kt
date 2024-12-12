package c14220230.paba.recyclerview

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    //private lateinit var _nama : Array<String>
    //private lateinit var _karakter : Array<String>
    //private lateinit var _deskripsi : Array<String>
    //private lateinit var _gambar : Array<String>

    //private lateinit var _nama : MutableList<String>
    //private lateinit var _karakter : MutableList<String>
    //private lateinit var _deskripsi : MutableList<String>
    //private lateinit var _gambar : MutableList<String>

    private lateinit var sp: SharedPreferences

    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _karakter: MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi: MutableList<String> = emptyList<String>().toMutableList()
    private var _gambar: MutableList<String> = emptyList<String>().toMutableList()

    private var arWayang = arrayListOf<wayang>()

    private lateinit var _rvWayang: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        _rvWayang = findViewById<RecyclerView>(R.id.rvWayang)
//        SiapkanData()
        if (arWayang.size == 0) {
            SiapkanData()
        } else {
            arWayang.forEach {
                _nama.add(it.nama)
                _gambar.add(it.foto)
                _karakter.add(it.karakter)
                _deskripsi.add(it.deskripsi)
            }
            arWayang.clear()
        }
        sp = getSharedPreferences("dataSP", MODE_PRIVATE)
        TambahData()
        TampilData()

        val gson = Gson()
        val isiSP = sp.getString("spWayang", null)
        val type = object : TypeToken<ArrayList<wayang>>() {}.type
        if (isiSP != null)
            arWayang = gson.fromJson(isiSP, type)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun SiapkanData() {
        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()
    }

    fun TambahData() {
        val gson = Gson()
        val editor = sp.edit()
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
        val json = gson.toJson(arWayang)
        editor.putString("spWayang", json)
        editor.apply()
    }

    fun TampilData() {
        _rvWayang.layoutManager = LinearLayoutManager(this)
//        _rvWayang.layoutManager = GridLayoutManager(this,2)
//        _rvWayang.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        _rvWayang.adapter = adapterRecView(arWayang)

        val adapterWayang = adapterRecView(arWayang)
        _rvWayang.adapter = adapterWayang

        adapterWayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: wayang) {
                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, detWayang::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah benar data" + _nama[pos] + "akan dihapus?")
                    .setPositiveButton(
                        "HAPUS", { dialog, which ->
                            _gambar.removeAt(pos)
                            _nama.removeAt(pos)
                            _deskripsi.removeAt(pos)
                            _karakter.removeAt(pos)
                            TambahData()
                            TampilData()
                        }
                    )
                    .setNegativeButton(
                        "BATAL", { dialog, which ->
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
