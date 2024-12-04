package com.example.recipehub

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipehub.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.more.setOnClickListener {
            var dialog=Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottum_sheet)

            dialog.show()

dialog.window!!.setLayout(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.WRAP_CONTENT

)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }




        binding.salad.setOnClickListener {
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("tittle","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }

        binding.Main.setOnClickListener {
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("tittle","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }

        binding.Drinks.setOnClickListener {
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("tittle","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)
        }


        binding.Dessert.setOnClickListener {
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("tittle","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)
        }


        setUpRecyclerView()
        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))


        }

    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.popularRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]!!.category.contains("Popular"))
                dataList.add(recipes[i]!!)
        }
        rvAdapter = PopularAdapter(dataList, this)
        binding.popularRecycler.adapter = rvAdapter
    }
}