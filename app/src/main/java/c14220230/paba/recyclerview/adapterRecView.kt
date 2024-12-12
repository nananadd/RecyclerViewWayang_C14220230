package c14220230.paba.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterRecView (private val listWayang: ArrayList<wayang>) : RecyclerView
.Adapter<adapterRecView.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaWayang= itemView.findViewById<TextView>(R.id.namaWayang)
        var _karakterWayang= itemView.findViewById<TextView>(R.id.karakterWayang)
        var _deskripsiWayang= itemView.findViewById<TextView>(R.id.deskripsiWayang)
        var _gambarWayang= itemView.findViewById<ImageView>(R.id.gambarWayang)
        var _btnHapusData= itemView.findViewById<Button>(R.id.btnHapus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listWayang.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var wayang = listWayang[position]

        holder._namaWayang.setText(wayang.nama)
        holder._deskripsiWayang.setText(wayang.deskripsi)
        holder._karakterWayang.setText(wayang.karakter)
        Log.d("TEST",wayang.foto)
        Picasso.get()
            .load(wayang.foto)
            .into(holder._gambarWayang)

        holder._gambarWayang.setOnClickListener {
//            Toast.makeText(holder.itemView.context,wayang.nama,Toast.LENGTH_SHORT).show()
            onItemClickCallback.onItemClicked(listWayang[position])
        }
        holder._btnHapusData.setOnClickListener {
            onItemClickCallback.delData(position)
        }
    }
    private lateinit var onItemClickCallback : OnItemClickCallback
    interface OnItemClickCallback {
        fun onItemClicked(data: wayang)
        fun delData(pos: Int)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}


