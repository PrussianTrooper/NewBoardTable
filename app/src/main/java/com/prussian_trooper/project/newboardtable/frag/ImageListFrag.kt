package com.prussian_trooper.project.newboardtable.frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.databinding.ListImageFragBinding
import com.prussian_trooper.project.newboardtable.utils.ImagePicker
import com.prussian_trooper.project.newboardtable.utils.ItemTouchMoveCallback

class ImageListFrag(private val fragCloseInterface : FragmentCloseInterface, private val newList : ArrayList<String>) : Fragment() {
    lateinit var rootElement : ListImageFragBinding
    val adapter = SelectImageRvAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootElement = ListImageFragBinding.inflate(inflater)
        return rootElement.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        touchHelper.attachToRecyclerView(rootElement.rcViewSelectImage)
        rootElement.rcViewSelectImage.layoutManager = LinearLayoutManager(activity)
        rootElement.rcViewSelectImage.adapter = adapter
        val updateList = ArrayList<SelectImageItem>()
        for (n in 0 until newList.size){
            updateList.add(SelectImageItem(n.toString(), newList[n]))
        }
        adapter.updateAdapter(updateList, true)


    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragClose(adapter.mainArray)
    }

   private fun setUpToolbar() {
       rootElement.tb.inflateMenu(R.menu.menu_choose_image)
       val deleteItem = rootElement.tb.menu.findItem(R.id.id_delete_image)
       val addImageItem = rootElement.tb.menu.findItem(R.id.id_add_image)

       rootElement.tb.setNavigationOnClickListener{
           activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
       }

       deleteItem.setOnMenuItemClickListener {
           adapter.updateAdapter(ArrayList(), true)
           true
       }

       addImageItem.setOnMenuItemClickListener {
           val imageCont = ImagePicker.MAX_IMAGE_COUNT - adapter.mainArray.size
           ImagePicker.getImages(activity as AppCompatActivity, imageCont)
           true
       }
   }

    fun updateAdapter(newList: ArrayList<String>) {
        val updateList = ArrayList<SelectImageItem>()
        for (n in adapter.mainArray.size until newList.size + adapter.mainArray.size){
            updateList.add(SelectImageItem(n.toString(), newList[n - adapter.mainArray.size]))
        }
        adapter.updateAdapter(updateList, false)
    }
}