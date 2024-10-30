package com.example.e_commerce_book_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class PurchaseActivity : AppCompatActivity() {
    lateinit var name: TextView
    lateinit var price: TextView
    lateinit var quantity: TextView
    lateinit var available: TextView
    lateinit var profile: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)
        name=findViewById(R.id.g_name)
        price=findViewById(R.id.g_price)
        quantity=findViewById(R.id.g_quantity)
        profile=findViewById(R.id.getting_image)
        getData()
    }
    private fun getData(){
        name.text = intent.getStringExtra("name")
        price.text = intent.getStringExtra("price")
        quantity.text = intent.getStringExtra("quantity")
        val profileurl = intent.getStringExtra("image")
        Glide.with(this).load(profileurl.toString()).into(profile)

    }

}