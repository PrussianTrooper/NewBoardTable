package com.prussian_trooper.project.newboardtable.frag

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.prussian_trooper.project.newboardtable.R
import com.prussian_trooper.project.newboardtable.adapters.ImageAdapter
import com.prussian_trooper.project.newboardtable.databinding.ListImageFragBinding
import com.prussian_trooper.project.newboardtable.dialogHelper.ProgressDialog
import com.prussian_trooper.project.newboardtable.utils.ImageManager
import com.prussian_trooper.project.newboardtable.utils.ImagePicker
import com.prussian_trooper.project.newboardtable.utils.ItemTouchMoveCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageListFrag(private val fragCloseInterface : FragmentCloseInterface, private val newList : ArrayList<String>?) : Fragment() {

    lateinit var rootElement : ListImageFragBinding
    val adapter = SelectImageRvAdapter()
    val dragCallback = ItemTouchMoveCallback(adapter)
    val touchHelper = ItemTouchHelper(dragCallback)
    private var job: Job? = null

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
        if (newList != null) resizeSelectedImages(newList, true)

    }

    fun updateAdapterFromEdir(bitmapList: List<Bitmap>){
        adapter.updateAdapter(bitmapList, true)
    }

    override fun onDetach() {
        super.onDetach()
        fragCloseInterface.onFragClose(adapter.mainArray)
        job?.cancel()
    }

    private fun resizeSelectedImages(newList : ArrayList<String>, needClear : Boolean){

        job = CoroutineScope(Dispatchers.Main).launch {
            val dialog = ProgressDialog.createProgressDialog(activity as Activity)
            dialog.dismiss()
            val bitmapList = ImageManager.imageResize(newList)
            adapter.updateAdapter(bitmapList, needClear)

        }
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
           ImagePicker.getImages(activity as AppCompatActivity, imageCont, ImagePicker.REQUEST_CODE_GET_IMAGES)
           true
       }
   }

    fun updateAdapter(newList: ArrayList<String>) { resizeSelectedImages(newList, false) }

    fun setSingleImage(uri : String, pos : Int){
        val pBar = rootElement.rcViewSelectImage[pos].findViewById<ProgressBar>(R.id.pBar)
        job = CoroutineScope(Dispatchers.Main).launch {
            pBar.visibility = View.VISIBLE
            val bitmapList = ImageManager.imageResize(listOf(uri))
            pBar.visibility = View.GONE
            adapter.mainArray[pos] = bitmapList[0]
            adapter.notifyItemChanged(pos)
        }

    }
}