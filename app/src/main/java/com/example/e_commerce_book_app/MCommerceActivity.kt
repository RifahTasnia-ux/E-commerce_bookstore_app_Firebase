package com.example.e_commerce_book_app

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce_book_app.databinding.ActivityMcommerceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MCommerceActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMcommerceBinding
    private lateinit var dataList: ArrayList<CategoryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMcommerceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnlogout.visibility = View.GONE
        firebaseAuth = FirebaseAuth.getInstance()
        getAllCategory()
        binding.btnlogout.setOnClickListener(){
            firebaseAuth.signOut()
            val settings: SharedPreferences =  getSharedPreferences("secretDB", Context.MODE_PRIVATE)
            settings.edit().remove("uid").apply()
            settings.edit().remove("isLoggedIn").apply()
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finishAffinity()

        }
    }

    private fun getAllCategory() {
        dataList = arrayListOf()
        db.collection("Category").get().addOnSuccessListener {
            if (it.isEmpty) {

            } else {
                it.documents.forEach { singledata ->
                    val model = singledata.toObject(CategoryModel::class.java)
                    if (model != null) {
                        dataList.add(model)
                    }
                }
                binding.loadingBar.visibility = View.GONE
                binding.categoryRecyllerview.visibility = View.VISIBLE
                binding.btnlogout.visibility = View.VISIBLE
                initRecyllerView()

            }
        }.addOnFailureListener {

        }
    }

    private fun initRecyllerView() {
        binding.categoryRecyllerview.hasFixedSize()
        binding.categoryRecyllerview.layoutManager = GridLayoutManager(this, 2)
        val adapter = CategoryAdapter(dataList)
        binding.categoryRecyllerview.adapter = adapter

        adapter.setOnCLickListener(object : CategoryAdapter.OnClickListener {
            override fun onClick(position: Int, categoryModel: CategoryModel) {
                val intent = Intent(this@MCommerceActivity,ProductActivity::class.java)
                intent.putExtra("code",categoryModel.name)
                startActivity(intent)
            }

        })
    }

}