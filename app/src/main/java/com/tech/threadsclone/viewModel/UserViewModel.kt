package com.tech.threadsclone.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tech.threadsclone.model.ThreadModel
import com.tech.threadsclone.model.UserModel
import com.tech.threadsclone.utils.SharedPrefrence
import java.util.UUID

class UserViewModel : ViewModel() {

    private val fdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    val threadRef: DatabaseReference = fdb.getReference("threads")
    val userRef: DatabaseReference = fdb.getReference("users")

    private val _threads = MutableLiveData(listOf<ThreadModel>())
    val threads : LiveData<List<ThreadModel>> get() = _threads

    private val _users = MutableLiveData(UserModel())
    val users : LiveData<UserModel> get() = _users

    fun fetchUser(uid:String){
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchThreads(uid:String){
        threadRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val threadList = snapshot.children.mapNotNull {
                    it.getValue(ThreadModel::class.java)
                }
                _threads.postValue(threadList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}