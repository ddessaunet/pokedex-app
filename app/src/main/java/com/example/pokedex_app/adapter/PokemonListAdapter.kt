package com.example.pokedex_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex_app.R
import com.example.pokedex_app.`interface`.IItemClickListener
import com.example.pokedex_app.common.Common
import com.example.pokedex_app.model.Pokemon

class PokemonListAdapter(
    internal val context:Context,
    internal val pokemonList:List<Pokemon>
):RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    override fun getItemCount():Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        Glide.with(context).load(pokemonList[position].img).into(holder.img_pokemon)
        holder.txt_pokemon.text = pokemonList[position].name

        /*
        * The object keyword can also be used to create objects of an anonymous class known as
        * anonymous objects. They are used if you need to create an object of a slight modification
        * of some class or interface without declaring a subclass for it.
        *
        * https://www.programiz.com/kotlin-programming/object-singleton
        * */
        holder.setItemClickListener(object:IItemClickListener{
            override fun onClick(view: View, position: Int) {
                Toast
                    .makeText(context, "Clicked at Pokemon: " + pokemonList[position].name, Toast.LENGTH_SHORT)
                    .show()

                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("position", position))
            }
        })
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        internal var img_pokemon:ImageView
        internal var txt_pokemon:TextView

        internal var itemClickListener:IItemClickListener?=null

        fun setItemClickListener(iItemClickListener: IItemClickListener)
        {
            this.itemClickListener = iItemClickListener
        }

        init {
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            txt_pokemon = itemView.findViewById(R.id.pokemon_name) as TextView

            itemView.setOnClickListener { view -> itemClickListener!!.onClick(view, adapterPosition)}
        }
    }

}