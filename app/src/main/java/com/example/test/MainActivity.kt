package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ActivityMainBinding
import com.example.test.model.Exhibit
import com.example.test.model.ExhibitLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var cardAdapter: CardAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        linearLayoutManager =  LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = linearLayoutManager

        getExhibit()

    }

    private fun getExhibit() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://my-json-server.typicode.com/Reyst/exhibit_db/")
            .build()
            .create(ExhibitLoader::class.java)

        val retrofitData = retrofitBuilder.getExhibitList()
        retrofitData.enqueue(object : Callback<List<Exhibit>?> {
            override fun onResponse( call: Call<List<Exhibit>?>, response: Response<List<Exhibit>?>)
            {
                val responseBody = response.body()!!

                cardAdapter = CardAdapter(baseContext, responseBody)
                binding.recyclerView.adapter = cardAdapter


            }

            override fun onFailure(call: Call<List<Exhibit>?>, t: Throwable) {

                d("MainActivity", "onFailure")
            }
        })
    }
}