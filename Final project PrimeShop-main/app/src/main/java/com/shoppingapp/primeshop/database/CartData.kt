package com.shoppingapp.primeshop.database

class CartData private constructor() {
    private val dataArray = mutableListOf<String>()

    companion object {
        // Singleton instance
        private var instance: CartData? = null

        // Get the instance of the UniversalArray
        fun getInstance(): CartData {
            if (instance == null) {
                instance = CartData()
            }
            return instance as CartData
        }
    }

    // Add data to the array
    fun addData(data: String) {
        dataArray.add(data)
    }

    // Get the data array
    fun getDataArray(): List<String> {
        return dataArray
    }
}
