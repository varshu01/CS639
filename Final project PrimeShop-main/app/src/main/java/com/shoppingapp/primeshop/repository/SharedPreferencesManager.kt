package com.shoppingapp.primeshop.repository

import android.content.Context

object SharedPreferencesManager {
    private const val PREFS_NAME = "CartPref"
    private const val KEY_STRING_LIST = "Cart Items"

    fun saveStringList(context: Context, stringList: List<String>) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet(KEY_STRING_LIST, HashSet(stringList))
        editor.apply()
    }

    fun getStringList(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val stringSet = sharedPreferences.getStringSet(KEY_STRING_LIST, HashSet()) ?: HashSet()
        return ArrayList(stringSet)
    }
}