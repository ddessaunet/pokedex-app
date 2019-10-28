package com.example.pokedex_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pokedex_app.common.Common
import com.example.pokedex_app.view.PokemonDetail
import com.example.pokedex_app.view.PokemonList
import com.example.pokedex_app.view.PokemonType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Create BroadCast handle
    private val showPokemonType = object:BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action!!.toString() == Common.KEY_POKEMON_TYPE)
            {
                //Replace Fragment
                val pokemonType = PokemonType.getInstance()
                val bundle = Bundle()
                val type = intent.getStringExtra("type")
                bundle.putString("type", type)
                pokemonType.arguments = bundle

                supportFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.list_pokemon_fragment, pokemonType)
                fragmentTransaction.addToBackStack("type")
                fragmentTransaction.commit()

                toolbar.title = "POKEMON TYPE " + type.toUpperCase()
            }
        }
    }

    private val showDetail = object:BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action!!.toString() == Common.KEY_ENABLE_HOME)
            {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)

                //Replace Fragment
                val detailFragment = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num = intent.getStringExtra("num")
                bundle.putString("num", num)
                detailFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

                //Set Pokemon Name for Toolbar
                val pokemon = Common.findPokemonByNum(num)
                toolbar.title = pokemon!!.name
            }
        }
    }

    //Create BroadCast handle
    private val showEvolution = object:BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action!!.toString() == Common.KEY_NUM_EVOLUTION)
            {
                //Replace Fragment
                val detailFragment = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num = intent.getStringExtra("num")
                bundle.putString("num", num)
                detailFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(detailFragment)
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

                //Set Pokemon Name for Toolbar
                val pokemon = Common.findPokemonByNum(num)
                toolbar.title = pokemon!!.name
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Register Broadcast
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showPokemonType, IntentFilter(Common.KEY_POKEMON_TYPE))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId)
        {
            android.R.id.home -> {
                toolbar.title = "Pokemon App"

                //Clear all fragment in stack with name 'detail'
                supportFragmentManager.popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.popBackStack("type", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                //Replace fragment
                val pokemonList = PokemonList.getInstance()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(pokemonList)
                fragmentTransaction.replace(R.id.list_pokemon_fragment, pokemonList)
                fragmentTransaction.commit()

                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setDisplayShowHomeEnabled(false)
            }
        }
        return true
    }
}
