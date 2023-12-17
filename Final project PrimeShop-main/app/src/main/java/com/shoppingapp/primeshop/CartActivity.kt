package com.shoppingapp.primeshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shoppingapp.primeshop.adapters.CartAdapter
import com.shoppingapp.primeshop.models.CartItems

class CartActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        val buttonview = findViewById<Button>(R.id.clearData)



        val listView = findViewById<ListView>(R.id.listView)

        // Replace "your_preference_name" with the actual name of your SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("CartPref", Context.MODE_PRIVATE)
        val allEntries: Map<String, *> = sharedPreferences.all

        val keyValueList: MutableList<CartItems> = mutableListOf()

        for ((key, value) in allEntries) {
            keyValueList.add(CartItems(key, value.toString()))
        }

        val adapter = CartAdapter(this, keyValueList)
        listView.adapter = adapter

     /*   fun accessList(context: Context) {
            // Retrieve the list from SharedPreferences
            val retrievedList = SharedPreferencesManager.getStringList(context)

            // Do something with the retrieved list
            for (item in retrievedList) {
                // Process each item as needed

            }
        }*/


    }


    private fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // You can also update your UI or display a message after clearing the data if needed
    }

    fun ClearData(view: View) {
        clearSharedPreferences()
        Toast.makeText(this,"Data Deleted",Toast.LENGTH_SHORT).show()
    }

    private fun isSharedPreferencesEmpty(): Boolean {
        val sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE)

        // Choose a key that you expect to be set if data is present
        val sampleKey = "Key"

        // Retrieve a value from SharedPreferences
        // The second parameter is the default value if the key is not present
        val storedValue = sharedPreferences.getString(sampleKey, "")

        // If the stored value is the default value, it means SharedPreferences is empty
        return storedValue == ""
    }


}