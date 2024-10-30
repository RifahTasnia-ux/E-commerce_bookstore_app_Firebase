package com.example.e_commerce_book_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.e_commerce_book_app.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSecretData()
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSubmit.setOnClickListener {
            binding.btnSubmit.visibility = View.GONE
            binding.progressbar.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            val number = binding.etMobile.text.toString()

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                    savedAccountInfo(it)
                    saveToDB(it, email, name, number)
                }
            }.addOnFailureListener {
                binding.btnSubmit.visibility = View.VISIBLE
                binding.progressbar.visibility = View.GONE
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveToDB(task: Task<AuthResult>, email: String, name: String, number: String) {
        val model = ProfileModel(task.result.user?.uid.toString(), name, email, number, false)
        db.collection("Profile").document(task.result.user?.uid.toString()).set(model)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, MCommerceActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }

    }

    private fun savedAccountInfo(task: Task<AuthResult>) {
        val sharedPreference = getSharedPreferences("secretDB", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("uid", task.result.user?.uid.toString())
        editor.apply()
    }

    private fun getSecretData() {
        val sharedPreference = getSharedPreferences("secretDB", Context.MODE_PRIVATE)
        val result = sharedPreference.getBoolean("isLoggedIn", false)
        if (result) {
            val intent = Intent(this, MCommerceActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}