package com.example.recipehub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipehub.databinding.ActivityCategoryBinding
import com.example.recipehub.databinding.ActivityHomeBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var rvAdapter:CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title=intent.getStringExtra("TITTLE")
        setUpRecyclerView()

        binding.backbtn.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.categoryRv.layoutManager =
            LinearLayoutManager(this)
        var db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!))
                dataList.add(recipes[i]!!)
        }
        rvAdapter = CategoryAdapter(dataList, this)
        binding.categoryRv.adapter = rvAdapter
    }
}