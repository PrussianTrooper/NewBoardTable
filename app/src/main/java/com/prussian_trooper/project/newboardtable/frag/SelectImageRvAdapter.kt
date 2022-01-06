package com.prussian_trooper.project.newboardtable.frag

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.act.EditAdsAct
import com.prussian_trooper.project.newboardtable.databinding.SelectImageFragItemBinding
import com.prussian_trooper.project.newboardtable.utils.AdapterCallback
import com.prussian_trooper.project.newboardtable.utils.ImageManager
import com.prussian_trooper.project.newboardtable.utils.ImagePicker
import com.prussian_trooper.project.newboardtable.utils.ItemTouchMoveCallback
import java.util.ArrayList

class SelectImageRvAdapter(val adapterCallback: AdapterCallback): RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>(), ItemTouchMoveCallback.ItemTouchAdapter {

    val mainArray = ArrayList<Bitmap>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {

        val viewBinding = SelectImageFragItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(viewBinding, parent.context, this)
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
        mainArray[starPos] = targetItem
        notifyItemMoved(starPos, targetPos)
    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(private val viewBinding: SelectImageFragItemBinding, val context : Context, val adapter : SelectImageRvAdapter) : RecyclerView.ViewHolder(viewBinding.root) {

        fun setData(bitMap : Bitmap){

            viewBinding.imEditImage.setOnClickListener{

                ImagePicker.getImages(context as EditAdsAct, 1, ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE)
                context.editImagePos = adapterPosition
            }

            viewBinding.imDelete.setOnClickListener {

                adapter.mainArray.removeAt(adapterPosition)
                adapter.notifyItemRemoved(adapterPosition)
                for (n in 0 until adapter.mainArray.size) adapter.notifyItemChanged(n)
                adapter.adapterCallback.onItemDelete()
            }

            viewBinding.tvTitle.text = context.resources.getStringArray(R.array.title_array)[adapterPosition]
            ImageManager.chooseScaleType(viewBinding.imageView, bitMap)
            viewBinding.imageView.setImageBitmap(bitMap)
        }
    }

    fun updateAdapter(newList : List<Bitmap>, needClear : Boolean) {//Update function for Image
        if (needClear) mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }
}