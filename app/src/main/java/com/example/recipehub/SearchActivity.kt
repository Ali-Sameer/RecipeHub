package com.example.recipehub

import android.annotation.SuppressLint
import android.content.Intent
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethod

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipehub.databinding.ActivitySearchBinding

import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var recipes: List<Recipe?>

    private lateinit var binding: ActivitySearchBinding

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.search1.requestFocus()
        var db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        recipes = daoObject.getAll()

        binding.backHome.setOnClickListener {
            finish()
        }

        setUpRecyclerView()
        binding.search1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "") {
                    filterData(s.toString())
                } else {
                    setUpRecyclerView()
                }
            }

            private fun filterData(filterText: String) {
                var filterData = ArrayList<Recipe>()
                for (i in recipes.indices) {
                    if (recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())) {
                        filterData.add(recipes[i]!!)

                    }
                    rvAdapter.filterList(filterList = filterData)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.searchRv.layoutManager =
            LinearLayoutManager(this)

        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular"))
                dataList.add(recipes[i]!!)
        }
        rvAdapter = SearchAdapter(dataList, this)
        binding.searchRv.adapter = rvAdapter
    }
}