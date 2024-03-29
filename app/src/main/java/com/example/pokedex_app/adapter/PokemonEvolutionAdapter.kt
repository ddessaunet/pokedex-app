package com.example.pokedex_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_app.R
import com.example.pokedex_app.common.Common
import com.example.pokedex_app.model.Evolution
import com.robertlevonyan.views.chip.Chip

class PokemonEvolutionAdapter(
    internal var context: Context,
    internal var evolutionList: List<Evolution>
):RecyclerView.Adapter<PokemonEvolutionAdapter.MyViewHolder>() {

    init {
        if (evolutionList == null) {
            evolutionList = ArrayList()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return evolutionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = evolutionList[position].name
        holder.chip.changeBackgroundColor(Common.getColorByType(Common.findPokemonByNum(evolutionList[position].num!!)!!.type!![0]))
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var chip: Chip

        init {
            chip = itemView.findViewById(R.id.chip) as Chip
            chip.setOnChipClickListener {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()

                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_NUM_EVOLUTION).putExtra("num", evolutionList[adapterPosition].num))
            }
        }
    }

}