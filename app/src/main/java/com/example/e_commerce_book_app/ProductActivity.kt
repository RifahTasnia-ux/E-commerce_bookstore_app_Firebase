package com.example.e_commerce_book_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerce_book_app.databinding.ActivityProductBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var myProductList: ArrayList<ProductModel>
    private lateinit var binding:ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
    }

    private fun getIntentData() {
        val code = intent.getStringExtra("code")
        getallproductfromDB(code)
    }
    private fun getallproductfromDB(code: String?){
        myProductList = arrayListOf()
        db.collection(code.toString()).get().addOnSuccessListener {
            println(code.toString())
            if(it.isEmpty){
                   Toast.makeText(this,"No books to Show.",Toast.LENGTH_SHORT).show()
            }else{
                it.documents.forEach{ singleProduct ->
                    val model = singleProduct.toObject(ProductModel::class.java)
                    if(model != null) {
                        myProductList.add(model)
                    }
                }
                initRecycllerView()

            }


        }.addOnFailureListener{
              Toast.makeText(applicationContext,it.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }
    private fun initRecycllerView(){
        binding.productRecycllerView.hasFixedSize()
        binding.productRecycllerView.layoutManager=GridLayoutManager(this,1)
        val adapter = ProductAdapter(myProductList)
        binding.productRecycllerView.adapter = adapter

        adapter.setOnCLickListener(object : ProductAdapter.OnClickListener {
            override fun onClick(position: Int, productModel: ProductModel) {
                val intent = Intent(this@ProductActivity,PurchaseActivity::class.java)
                intent.putExtra("id",productModel.id)
                intent.putExtra("name",productModel.name)
                intent.putExtra("image",productModel.image)
                intent.putExtra("price",productModel.price)
                intent.putExtra("quantity",productModel.quantity)

                startActivity(intent)
            }

        })

    }
}