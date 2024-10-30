package com.example.e_commerce_book_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val list:ArrayList<ProductModel>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var mOnCLickListener: ProductAdapter.OnClickListener? = null
    class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.product_sample_name)
        val image = itemView.findViewById<ImageView>(R.id.product_image)
        val price = itemView.findViewById<TextView>(R.id.product_sample_price)
        val quantity = itemView.findViewById<TextView>(R.id.product_sample_quantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.product_sample_layout,parent,false)
        return ProductViewHolder(item)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val singleProduct=list[position]
        holder.title.text = singleProduct.name.toString()
        holder.price.text = singleProduct.price.toString()
        holder.quantity.text = singleProduct.quantity.toString()
        Glide.with(holder.image.context).load(singleProduct.image).into(holder.image)
        holder.itemView.setOnClickListener {
            if (mOnCLickListener != null) {
                mOnCLickListener!!.onClick(position, list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setOnCLickListener(onCLickListener: OnClickListener) {
        this.mOnCLickListener = onCLickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, productModel: ProductModel)
    }

}