package com.tech.threadsclone.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPrefrence {

    fun storeData(
        email: String,
        password: String,
        name: String,
        bio: String,
        userName: String,
        imageUrl: String,
        context: Context
    ) {
        val SharedPrefrence = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = SharedPrefrence.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("name", name)
        editor.putString("bio", bio)
        editor.putString("userName", userName)
        editor.putString("imageUrl", imageUrl)
        editor.apply()
    }

    fun getEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email", "")!!
    }

    fun getPassword(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("password", "")!!
    }

    fun getName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("name", "")!!
    }

    fun getBio(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("bio", "")!!
    }

    fun getUserName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("userName", "")!!
    }

    fun getImageUrl(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl", "")!!
    }
}