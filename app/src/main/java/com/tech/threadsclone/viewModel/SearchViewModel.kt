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

class SearchViewModel : ViewModel() {

    private val fdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    val users: DatabaseReference = fdb.getReference("users")

    private var _users = MutableLiveData<List<UserModel>>()
    val usersList: LiveData<List<UserModel>> = _users

    init {
        fetchUsers {
            _users.value = it
        }
    }

    private fun fetchUsers(onResult: (List<UserModel>) -> Unit) {
        users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<UserModel>()
                for (threadsSnapshot in snapshot.children) {
                    val user = threadsSnapshot.getValue(UserModel::class.java)
                    result.add(user!!)
                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromThreads(thread: ThreadModel, onResult: (UserModel) -> Unit) {
        fdb.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let { onResult }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}