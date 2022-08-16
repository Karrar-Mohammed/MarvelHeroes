package com.karrar.marvelheroes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.karrar.marvelheroes.R
import com.karrar.marvelheroes.data.State
import com.karrar.marvelheroes.data.network.Client
import com.karrar.marvelheroes.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {

            val flow = flow {
                emit(Client.getMovie(10))
            }.flowOn(Dispatchers.IO)
        lifecycleScope.launch {
            flow.collect{
                if (it is State.Success){
                    Log.v("testApi", it.data.toString())

                }else {
                    Log.v("testApi", it.toString())
                }
            }
        }
    }
}