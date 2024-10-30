package com.example.e_commerce_book_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        delay5Seconds()
    }
    private fun delay5Seconds(){
        Handler(Looper.getMainLooper()).postDelayed({
            gotoapp() },
            3000)
        }
    private fun gotoapp(){
        startActivity(Intent(this,MCommerceActivity::class.java))
        finish()
    }
}
