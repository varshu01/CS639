package com.shoppingapp.primeshop

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.shoppingapp.primeshop.repository.SharedPreferencesManager
import com.squareup.picasso.Picasso

class ProductActivity : AppCompatActivity() {

    lateinit var sizeText :TextView
    lateinit var buyButton :Button


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val titleTextView: TextView = findViewById(R.id.title_textView)
        val priceTextView: TextView = findViewById(R.id.price_textView)
        val descriptionTextView: TextView = findViewById(R.id.des_textView)
        val clothImageView: ImageView = findViewById(R.id.imgShow)

      sizeText  = findViewById<TextView>(R.id.size_menu)

        buyButton  = findViewById<Button>(R.id.buyItem)


        val vTitle = intent.getStringExtra("key_title")
        val vPrice = intent.getStringExtra("key_price")
        val vDescription = intent.getStringExtra("key_des")
        val vImageUrl = intent.getStringExtra("key_imageUrl")

        Picasso.get().load(vImageUrl).into(clothImageView);


        buyButton.setOnClickListener(){

            val intent = Intent(this, BuyActivity::class.java)

            // Pass the value to SecondActivity using Intent
            intent.putExtra("key_title", vTitle)
            intent.putExtra("key_price", vPrice)
            intent.putExtra("key_imageUrl", vImageUrl)

            startActivity(intent)
        }



        titleTextView.text = vTitle
        priceTextView.text = "$"+ vPrice
        descriptionTextView.text = vDescription


        // Get the current list from SharedPreferences
        val existingList = SharedPreferencesManager.getStringList(this)

        // Add the new text to the list
        val newList = ArrayList(existingList)
        newList.add(vTitle+"-"+vPrice+"\n"+"\n")


        // Save the updated list to SharedPreferences
        SharedPreferencesManager.saveStringList(this, newList)

    }

    fun sizeGuide(view: View) {

        val sizeIntent = Intent(this, PaymentActivity::class.java)
        startActivity(sizeIntent)
    }

    fun addToCart(view: View) {
      /*  val sizeIntent = Intent(this, CartActivity::class.java)
        startActivity(sizeIntent)*/
        // on addtocart pressed add item name, image and price to the list
Toast.makeText(this,"Added to Cart",Toast.LENGTH_SHORT).show()
        //later calculate all prices
    }

    fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.sizes, popupMenu.menu)

        // Optional: Add item click listener
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.size_item1 -> {

                    sizeText.setText("Size - xs").toString()
                    true
                }

                R.id.size_item2 -> {
                    // Handle item 2 click
                    sizeText.setText("Size - s").toString()
                    true
                }

                R.id.size_item3 -> {
                    // Handle item 2 click
                    sizeText.setText("Size - m").toString()
                    true
                }

                R.id.size_item4 -> {
                    // Handle item 2 click
                    sizeText.setText("Size - l").toString()
                    true
                }

                R.id.size_item5 -> {
                    // Handle item 2 click
                    sizeText.setText("Size - xl").toString()
                    true
                }
                // Add more cases if needed
                else -> false
            }


        }
        popupMenu.show()
    }

}


