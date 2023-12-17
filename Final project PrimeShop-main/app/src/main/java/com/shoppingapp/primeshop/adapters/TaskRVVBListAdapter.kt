package com.shoppingapp.primeshop.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.primeshop.databinding.ViewTaskGridLayoutBinding
import com.shoppingapp.primeshop.databinding.ViewTaskListLayoutBinding
import com.shoppingapp.primeshop.models.Task
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale




class TaskRVVBListAdapter(

    private val isList: MutableLiveData<Boolean>,
    //val onClickListener: () -> Unit,


    private val deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
) :


    ListAdapter<Task,RecyclerView.ViewHolder>(DiffCallback()) {



    class ListTaskViewHolder(private val viewTaskListLayoutBinding: ViewTaskListLayoutBinding) :
        RecyclerView.ViewHolder(viewTaskListLayoutBinding.root) {


      fun bind(
             task: Task,
            deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
        ) {
            viewTaskListLayoutBinding.titleTxt.text = task.title
            viewTaskListLayoutBinding.descrTxt.text = task.description
            viewTaskListLayoutBinding.priceTxt.text = "$"+task.price
            viewTaskListLayoutBinding.categoryTxt.text = "Category - "+task.category

            val imageUri = task.imageUrl
            Picasso.get().load(imageUri).into(viewTaskListLayoutBinding.imageUrl);


            // get the item id from delete button then pass the value through shared prefrences..to the next page...
            //put a invisble relative layout on top of the laayout when user clicks it fetches everything and moves to next screen.


            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())






            viewTaskListLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            viewTaskListLayoutBinding.imageUrl.setOnClickListener{
                //do it here



            }


            viewTaskListLayoutBinding.redirectAction.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", adapterPosition, task)
                }
            }
            viewTaskListLayoutBinding.editImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update", adapterPosition, task)
                }
            }
        }



    }


    class GridTaskViewHolder(private val viewTaskGridLayoutBinding: ViewTaskGridLayoutBinding) :
        RecyclerView.ViewHolder(viewTaskGridLayoutBinding.root) {




        fun bind(
            task: Task,
            deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
        ) {
            viewTaskGridLayoutBinding.titleTxt.text = task.title
            viewTaskGridLayoutBinding.descrTxt.text = task.description
            viewTaskGridLayoutBinding.priceTxt.text = "$"+task.price
            viewTaskGridLayoutBinding.categoryTxt.text = "Category - "+task.category
           // viewTaskGridLayoutBinding.imageUrl.setBackgroundResource(R.drawable.ic_delete)
            val imageUri = task.imageUrl
            Picasso.get().load(imageUri).into(viewTaskGridLayoutBinding.imageUrl);




            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())

            viewTaskGridLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            viewTaskGridLayoutBinding.deleteImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", adapterPosition, task)
                }
            }
            viewTaskGridLayoutBinding.editImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update", adapterPosition, task)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (viewType == 1){  // Grid_Item
            GridTaskViewHolder(
                ViewTaskGridLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{  // List_Item
            ListTaskViewHolder(
                ViewTaskListLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)




        if (isList.value!!){
            (holder as ListTaskViewHolder).bind(task,deleteUpdateCallback)
        }else{
            (holder as GridTaskViewHolder).bind(task,deleteUpdateCallback)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (isList.value!!){
            0 // List_Item
        }else{
            1 // Grid_Item
        }
    }



    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

}