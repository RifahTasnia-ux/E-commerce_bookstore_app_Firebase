package com.example.e_commerce_book_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryAdapter(private val dataList: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var mOnCLickListener: OnClickListener? = null

    class CategoryViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val name = itemVIew.findViewById<TextView>(R.id.sample_title)
        val image = itemVIew.findViewById<ImageView>(R.id.sample_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.mcommerce_sample_layout, parent, false)
        return CategoryViewHolder(item)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.name.text = dataList[position].name
        Glide.with(holder.image.context).load(dataList[position].image).into(holder.image)
        holder.itemView.setOnClickListener {
            if (mOnCLickListener != null) {
                mOnCLickListener!!.onClick(position, dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setOnCLickListener(onCLickListener: OnClickListener) {
        this.mOnCLickListener = onCLickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, categoryModel: CategoryModel)
    }

}