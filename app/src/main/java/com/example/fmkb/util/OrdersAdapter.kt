package com.example.fmkb.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fmkb.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderAdapter(
    private val context: Context,
    private val orderClickInterface: OrderClickInterface
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val allOrders = ArrayList<Order>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv = itemView.findViewById<TextView>(R.id.tvTitle)
        val descriptionTv = itemView.findViewById<TextView>(R.id.tvDescription)
        val dateTv = itemView.findViewById<TextView>(R.id.tvDate)
        val imageIv = itemView.findViewById<ImageView>(R.id.ivImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_rv_order,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTv.text = allOrders[position].title
        holder.descriptionTv.text = UrlUtils.getDescription(allOrders[position].description)
        holder.dateTv.text =
            context.getString(
                R.string.label_updated_at,
                LocalDate.parse(allOrders[position].modificationDate, dateFormatter)
            )

        Glide.with(context)
            .load(allOrders[position].image_url)
            .override(250, 400)
            .placeholder(R.drawable.ic_broken_image)
            .dontAnimate()
            .into(holder.imageIv)

        holder.itemView.setOnClickListener {
            orderClickInterface.onOrderClick(allOrders[position])
        }
    }

    override fun getItemCount(): Int {
        return allOrders.size
    }

    fun updateList(newList: List<Order>) {
        allOrders.clear()
        allOrders.addAll(newList)
        notifyDataSetChanged()
    }
}

interface OrderClickInterface {
    fun onOrderClick(order: Order)
}