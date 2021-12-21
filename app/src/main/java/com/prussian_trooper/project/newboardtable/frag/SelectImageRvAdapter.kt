package com.prussian_trooper.project.newboardtable.frag

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.utils.ItemTouchMoveCallback
import java.util.ArrayList

class SelectImageRvAdapter: RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>(), ItemTouchMoveCallback.ItemTouchAdapter {

    val mainArray = ArrayList<SelectImageItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_image_frag_item, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onMove(starPos: Int, targetPos: Int) { //Items(images) moving
        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[starPos]
        val titleStart = mainArray[targetPos].title
        mainArray[targetPos].title = targetItem.title
        mainArray[starPos] = targetItem
        mainArray[starPos].title = titleStart
        notifyItemMoved(starPos, targetPos)
    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tvTitle : TextView
        lateinit var image : ImageView
        fun setData(item : SelectImageItem){
            tvTitle = itemView.findViewById(R.id.tvTitle)
            image = itemView.findViewById(R.id.imageView)
            tvTitle.text = item.title
            image.setImageURI(Uri.parse(item.imageUri))

        }
    }

    fun updateAdapter(newList : List<SelectImageItem>) {//Update function for Image
        mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}