package com.shoppingapp.primeshop

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class BuyActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val tTextView: TextView = findViewById(R.id.title_buy)
        val PTextView: TextView = findViewById(R.id.title_price)
        val imageView: ImageView= findViewById(R.id.showImage)




        val vTitle = intent.getStringExtra("key_title")
        val vPrice = intent.getStringExtra("key_price")
        val vImageUrl = intent.getStringExtra("key_imageUrl")

        tTextView.setText(vTitle).toString()
        PTextView.setText(vPrice).toString()

        Picasso.get().load(vImageUrl).into(imageView);



    }

    fun freeCart(view: View) {


    }
}