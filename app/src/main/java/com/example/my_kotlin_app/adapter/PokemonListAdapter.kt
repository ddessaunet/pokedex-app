package com.example.my_kotlin_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_kotlin_app.R
import com.example.my_kotlin_app.model.Pokemon

class PokemonListAdapter(
    internal val context: Context,
    internal val pokemonList: List<Pokemon>
): RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonList[position].img).into(holder.img_pokemon)
        holder.txt_pokemon.text = pokemonList[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var img_pokemon: ImageView
        internal var txt_pokemon: TextView

        init {
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            txt_pokemon = itemView.findViewById(R.id.pokemon_name) as TextView
        }
    }

}