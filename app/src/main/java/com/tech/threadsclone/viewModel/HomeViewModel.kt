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

class HomeViewModel : ViewModel() {

    private val fdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    val thread: DatabaseReference = fdb.getReference("threads")

    private var _threadsAndUsers = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadsAndUsers: LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUsers

    init {
        fetchThreadsAndUsers {
            _threadsAndUsers.value = it
        }
    }

    private fun fetchThreadsAndUsers(onResult: (List<Pair<ThreadModel,UserModel>>) -> Unit){
        thread.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<ThreadModel,UserModel>>()
                for (threadsSnapshot in snapshot.children){
                    val thread = threadsSnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchUserFromThreads(it!!){
                            user ->
                            result.add(0,it to user)
                            if(result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromThreads(thread: ThreadModel, onResult:(UserModel) -> Unit){
        fdb.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let { onResult }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}