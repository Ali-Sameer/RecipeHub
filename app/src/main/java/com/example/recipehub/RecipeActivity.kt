package com.example.recipehub

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.recipehub.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    var imgCrop =true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
        binding.tittle.text=intent.getStringExtra("tittle")
       // binding.ingData.text=intent.getStringExtra("ing")
        binding.stepsData.text=intent.getStringExtra("des")
       var ing = intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }
          ?.toTypedArray()
        binding.time.text=ing?.get(0)

       for (i in 1 until ing!!.size){
           binding.ingData.text=
               """${binding.ingData.text} 🟢 ${ing[i]}
                   
               """.trimIndent()
        }
        binding.steps.background=null
        binding.steps.setTextColor(getColor(R.color.black))
        binding.steps.setOnClickListener {
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ing.background=null
            binding.ing.setTextColor(getColor(R.color.black))
            binding.stepsScroll.visibility=View.VISIBLE
            binding.ingScroll.visibility=View.GONE
        }

        binding.ing.setOnClickListener{

            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ing.setTextColor(getColor(R.color.white))
            binding.steps.background=null
            binding.steps.setTextColor(getColor(R.color.black))

            binding.stepsScroll.visibility=View.GONE
            binding.ingScroll.visibility=View.VISIBLE
        }

        binding.fullScreen.setOnClickListener {
            if (imgCrop){
                binding.itemImage.scaleType=ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
                binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility= View.GONE
                imgCrop=!imgCrop
            }else{
                binding.itemImage.scaleType=ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImage)
                binding.fullScreen.setColorFilter(null)
                binding.shade.visibility= View.GONE
                imgCrop=!imgCrop
            }

        }
        binding.backbtn.setOnClickListener {
            finish()
        }
    }
}