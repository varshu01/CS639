package com.shoppingapp.primeshop


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View

import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

import com.shoppingapp.primeshop.adapters.TaskRVVBListAdapter

import com.shoppingapp.primeshop.models.Task
import com.shoppingapp.primeshop.utils.Status
import com.shoppingapp.primeshop.utils.StatusResult
import com.shoppingapp.primeshop.utils.StatusResult.Added
import com.shoppingapp.primeshop.utils.StatusResult.Deleted
import com.shoppingapp.primeshop.utils.StatusResult.Updated
import com.shoppingapp.primeshop.utils.clearEditText
import com.shoppingapp.primeshop.utils.hideKeyBoard
import com.shoppingapp.primeshop.utils.longToastShow
import com.shoppingapp.primeshop.utils.setupDialog
import com.shoppingapp.primeshop.utils.validateEditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.shoppingapp.primeshop.databinding.ActivityMainBinding
import com.shoppingapp.primeshop.viewmodels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


private lateinit var mListener: OnItemClickListener

interface OnItemClickListener : AdapterView.OnItemClickListener {
    fun onItemClick(position: Int)
}

fun setOnItemClickListener(listener: OnItemClickListener) {
    mListener = listener
}

class MainActivity : AppCompatActivity() {


    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }




    private val addTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.add_task_dialog)
        }
    }

    private val updateTaskDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.update_task_dialog)
        }
    }

    private val loadingDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme).apply {
            setupDialog(R.layout.loading_dialog)
        }
    }

    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this)[TaskViewModel::class.java]
    }

    private val isListMutableLiveData = MutableLiveData<Boolean>().apply {
        postValue(true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)


        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel("https://a.cdn-hotels.com/gdcs/production171/d1558/d5250534-92b6-413c-bedf-2b9ac96e96fe.jpg?impolicy=fcrop&w=800&h=533&q=medium"))
        imageList.add(SlideModel("https://conclud.com/wp-content/uploads/2023/10/1620662805_221583.jpeg"))
        imageList.add(SlideModel("https://img.freepik.com/free-photo/portrait-happy-lady-sunglasses-standing-with-colorful-shopping-bags-hands-pink-background-young-woman-standing-white-shirt-denim-shorts_574295-1182.jpg?size=626&ext=jpg&ga=GA1.1.1546980028.1702598400&semt=ais"))

        val imageSlider = findViewById<ImageSlider>(R.id.content_image)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)


        // Add task start
        val addCloseImg = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        addCloseImg.setOnClickListener { addTaskDialog.dismiss() }

        val addETTitle = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val addETTitleL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        addETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETTitle, addETTitleL)
            }

        })

        val addETDesc = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val addETDescL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        addETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETDesc, addETDescL)
            }
        })

        val addETPrice = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskPrice)
        val addETPriceL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskPriceL)

        addETPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETPrice, addETPriceL)
            }
        })


        val addETCategory = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskCategory)
        val addETCategoryL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskCategoryL)

        addETCategory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETCategory, addETCategoryL)
            }
        })

        val addETImageUrl = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskImageUrl)
        val addETImageUrlL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskImageUrlL)

        addETImageUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETImageUrl, addETImageUrlL)
            }
        })

        mainBinding.addTaskFABtn.setOnClickListener {
            clearEditText(addETTitle, addETTitleL)
            clearEditText(addETDesc, addETDescL)
            clearEditText(addETPrice, addETPriceL)
            clearEditText(addETCategory, addETCategoryL)
            clearEditText(addETImageUrl, addETImageUrlL)


            addTaskDialog.show()
        }
        val saveTaskBtn = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        saveTaskBtn.setOnClickListener {
            if (validateEditText(addETTitle, addETTitleL)
                && validateEditText(addETDesc, addETDescL)
            ) {

                val newTask = Task(
                    UUID.randomUUID().toString(),
                    addETTitle.text.toString().trim(),
                    addETDesc.text.toString().trim(),
                    Date(),
                    addETPrice.text.toString().trim(),
                    addETCategory.text.toString().trim(),
                    addETImageUrl.text.toString().trim()


                )
                hideKeyBoard(it)
                addTaskDialog.dismiss()
                taskViewModel.insertTask(newTask)
            }
        }
        // Add task end


        // Update Task Start
        val updateETTitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val updateETTitleL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)



        updateETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETTitle, updateETTitleL)
            }

        })

        val updateETDesc = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val updateETDescL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)



        updateETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETDesc, updateETDescL)
            }
        })


        val updateETPrice = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskPrice)
        val updateETPriceL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskPriceL)


        updateETPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETPrice, updateETPriceL)
            }
        })



        val updateETCategory = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskCategory)
        val updateETCategoryL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskCategoryL)

        updateETCategory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETCategory, updateETCategoryL)
            }
        })


        // Update Task Start
        val updateETImageUrl = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskImageUrl)
        val updateETImageUrlL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskImageUrlL)
        updateETImageUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETImageUrl, updateETImageUrlL)


            }
        })


        val updateCloseImg = updateTaskDialog.findViewById<ImageView>(R.id.closeImg)
        updateCloseImg.setOnClickListener { updateTaskDialog.dismiss() }

        val updateTaskBtn = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)

        // Update Task End

        isListMutableLiveData.observe(this){
            if (it){
                mainBinding.taskRV.layoutManager = LinearLayoutManager(
                    this,LinearLayoutManager.VERTICAL,false
                )
                mainBinding.listOrGridImg.setImageResource(R.drawable.ic_view_module)
            }else{
                mainBinding.taskRV.layoutManager = StaggeredGridLayoutManager(
                    2,LinearLayoutManager.VERTICAL
                )
                mainBinding.listOrGridImg.setImageResource(R.drawable.ic_view_list)
            }
        }

        mainBinding.listOrGridImg.setOnClickListener {
            isListMutableLiveData.postValue(!isListMutableLiveData.value!!)
        }


        val taskRVVBListAdapter = TaskRVVBListAdapter(isListMutableLiveData ) {
        type, position, task ->


            if (type == "delete") {
                val intent = Intent(this, ProductActivity::class.java)

                // Pass the value to SecondActivity using Intent
                intent.putExtra("key_title", task.title)
                intent.putExtra("key_des", task.description)
                intent.putExtra("key_category", task.category)
                intent.putExtra("key_price", task.price)
                intent.putExtra("key_imageUrl", task.imageUrl)

                startActivity(intent)

            // Toast.makeText(this,"this is a toast $s123",Toast.LENGTH_SHORT).show()




            } else if (type == "update") {
                updateETTitle.setText(task.title)
                updateETDesc.setText(task.description)
                updateTaskBtn.setOnClickListener {
                    if (validateEditText(updateETTitle, updateETTitleL)
                        && validateEditText(updateETDesc, updateETDescL)
                    ) {
                        val updateTask = Task(
                            task.id,
                            updateETTitle.text.toString().trim(),
                            updateETDesc.text.toString().trim(),

//                           here i Date updated
                            Date(),
                            updateETCategory.text.toString().trim(),
                            updateETPrice.text.toString().trim(),
                            updateETImageUrl.text.toString().trim()
                        )
                        hideKeyBoard(it)
                        updateTaskDialog.dismiss()
                        taskViewModel
                            .updateTask(updateTask)
//                            .updateTaskPaticularField(
//                                task.id,
//                                updateETTitle.text.toString().trim(),
//                                updateETDesc.text.toString().trim()
//                            )
                    }
                }
                updateTaskDialog.show()
            }


        }


        mainBinding.taskRV.adapter = taskRVVBListAdapter
        ViewCompat.setNestedScrollingEnabled(mainBinding.taskRV,false)
        taskRVVBListAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
//                mainBinding.taskRV.smoothScrollToPosition(positionStart)
                mainBinding.nestedScrollView.smoothScrollTo(0,positionStart)
            }
        })
        callGetTaskList(taskRVVBListAdapter)
        callSortByLiveData()
        statusCallback()

        callSearch()

    }



    private fun restoreDeletedTask(deletedTask : Task){
        val snackBar = Snackbar.make(
            mainBinding.root, "Deleted '${deletedTask.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo"){
            taskViewModel.insertTask(deletedTask)
        }
        snackBar.show()
    }

    private fun callSearch() {
        mainBinding.edSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(query: Editable) {
                if (query.toString().isNotEmpty()){
                    taskViewModel.searchTaskList(query.toString())
                }else{
                    callSortByLiveData()
                }
            }
        })

        mainBinding.edSearch.setOnEditorActionListener{ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                hideKeyBoard(v)
                return@setOnEditorActionListener true
            }
            false
        }

        callSortByDialog()
    }
    private fun callSortByLiveData(){
        taskViewModel.sortByLiveData.observe(this){
            taskViewModel.getTaskList(it.second,it.first)
        }
    }

    private fun callSortByDialog() {
        var checkedItem = 0   // 2 is default item set
        val items = arrayOf("Letter (A-Z)", "Letter (Z-A)","Recently Added")

        mainBinding.sortBy.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Sort By")
                .setPositiveButton("Ok") { _, _ ->
                    when (checkedItem) {
                        0 -> {
                            taskViewModel.setSortBy(Pair("title",true))
                        }
                        1 -> {
                            taskViewModel.setSortBy(Pair("title",false))
                        }
                        2 -> {
                            taskViewModel.setSortBy(Pair("date",false))
                            // taskViewModel.setSortBy(Pair("date",true))

                        }
                        else -> {
                            //taskViewModel.setSortBy(Pair("date",false))
                        }
                    }
                }
                .setSingleChoiceItems(items, checkedItem) { _, selectedItemIndex ->
                    checkedItem = selectedItemIndex
                }
                .setCancelable(false)
                .show()
        }


    }

    private fun statusCallback() {
        taskViewModel
            .statusLiveData
            .observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        when (it.data as StatusResult) {
                            Added -> {
                                Log.d("StatusResult", "Added")
                            }

                            Deleted -> {
                                Log.d("StatusResult", "Deleted")

                            }

                            Updated -> {
                                Log.d("StatusResult", "Updated")

                            }
                        }
                        it.message?.let { it1 -> longToastShow(it1) }
                    }

                    Status.ERROR -> {
                        loadingDialog.dismiss()
                        it.message?.let { it1 -> longToastShow(it1) }
                    }
                }
            }
    }

    private fun callGetTaskList(taskRecyclerViewAdapter: TaskRVVBListAdapter) {

        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel
                .taskStateFlow
                .collectLatest {
                    Log.d("status", it.status.toString())

                    when (it.status) {
                        Status.LOADING -> {
                            loadingDialog.show()
                        }

                        Status.SUCCESS -> {
                            loadingDialog.dismiss()
                            it.data?.collect { taskList ->
                                taskRecyclerViewAdapter.submitList(taskList)
                            }
                        }

                        Status.ERROR -> {
                            loadingDialog.dismiss()
                            it.message?.let { it1 -> longToastShow(it1) }
                        }
                    }

                }
        }
    }

    fun makeitVisible(view: View) {
        mainBinding.addTaskFABtn.visibility = View.VISIBLE

    }



    fun cartClass(view: View) {

        val intentCart=Intent(this,CartActivity::class.java)
        startActivity(intentCart)
    }

    fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.category, popupMenu.menu)

        // Optional: Add item click listener
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.size_item1 -> {

                    mainBinding.edSearch.setText("Shirt").toString()
                    callSearch()
                    true
                }

                R.id.size_item2 -> {
                    // Handle item 2 click
                    mainBinding.edSearch.setText("Pants").toString()
                    callSearch()
                    true
                }

                R.id.size_item3 -> {
                    // Handle item 2 click
                    mainBinding.edSearch.setText("Dress").toString()
                    callSearch()
                    true
                }

                R.id.size_item4 -> {
                    // Handle item 2 click
                    mainBinding.edSearch.setText("Coat").toString()
                    callSearch()
                    true
                }

                R.id.size_item5 -> {
                    // Handle item 2 click
                    mainBinding.edSearch.setText("Blazer").toString()
                    callSearch()
                    true
                }
                R.id.size_item6-> {
                    // Handle item 2 click
                    mainBinding.edSearch.setText("Shoes").toString()
                    callSearch()
                    true
                }
                // Add more cases if needed
                else -> false
            }


        }
        popupMenu.show()
    }

}
