package com.shoppingapp.primeshop.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shoppingapp.primeshop.models.Task
import com.shoppingapp.primeshop.repository.TaskRepository


class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)
    val taskStateFlow get() =  taskRepository.taskStateFlow
    val statusLiveData get() =  taskRepository.statusLiveData
    val sortByLiveData get() =  taskRepository.sortByLiveData

    fun setSortBy(sort:Pair<String,Boolean>){
        taskRepository.setSortBy(sort)
    }

    fun getTaskList(isAsc : Boolean, sortByName:String) {
        taskRepository.getTaskList(isAsc, sortByName)
    }

    fun insertTask(task: Task){
        taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task) {
        taskRepository.deleteTask(task)
    }

    fun deleteTaskUsingId(taskId: String){
        taskRepository.deleteTaskUsingId(taskId)
    }

    fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    fun updateTaskPaticularField(taskId: String,title:String,description:String,price:String,category:String,imageUrl:String) {
        taskRepository.updateTaskPaticularField(taskId, title, description,price,category,imageUrl)
    }
    fun searchTaskList(query: String){
        taskRepository.searchTaskList(query)
    }
}