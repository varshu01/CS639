package com.example.helloworldkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonZoomIn: Button = findViewById(R.id.click_me_button)
        val textView: TextView = findViewById(R.id.hello)

        buttonZoomIn.setOnClickListener() {

            // loading the animation of
            // zoom_in.xml file into a variable
            val animZoomIn = AnimationUtils.loadAnimation(this,
                R.anim.zoom_in)

            animZoomIn.duration = 5000


                textView.startAnimation(animZoomIn)
        }
    }

}