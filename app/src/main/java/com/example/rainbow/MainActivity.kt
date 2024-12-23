package com.example.rainbow

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MainActivity : ComponentActivity() {

    lateinit var imageView: ImageView
    lateinit var textView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rainbowWheel = findViewById<RainbowWheelView>(R.id.rainbowWheelView)
        imageView = findViewById<ImageView>(R.id.image)
        textView = findViewById<TextView>(R.id.text)

        rainbowWheel.setWheelListener { index ->
            when(index) {
                0 -> showContent(Content(url = "https://randomuser.me/api/portraits/men/75.jpg"))
                1 -> showContent(Content(text = "dog"))
                2 -> showContent(Content(url = "https://randomuser.me/api/portraits/men/75.jpg"))
                3 -> showContent(Content(text = "mouse"))
                4 -> showContent(Content(url = "https://randomuser.me/api/portraits/men/75.jpg"))
                5 -> showContent(Content(text = "cat"))
                6 -> showContent(Content(url = "https://randomuser.me/api/portraits/men/75.jpg"))
            }
        }

        val spinButton = findViewById<Button>(R.id.spinButton)
        spinButton.setOnClickListener {
            rainbowWheel.startSpinning()
        }
    }

    private fun showContent(content: Content) {
        if (isDestroyed) return

        imageView.isVisible = false
        textView.isVisible = false

        if (content.url != null) {
            Glide.with(this)
                .load(content.url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
            imageView.isVisible = true
        }

        if (content.text != null) {
            textView.text = content.text
            textView.isVisible = true
        }
    }

    class Content(val url: String? = null, val text: String? = null)
}
