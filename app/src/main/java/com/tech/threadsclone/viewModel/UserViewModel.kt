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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tech.threadsclone.model.ThreadModel
import com.tech.threadsclone.model.UserModel
import com.tech.threadsclone.utils.SharedPrefrence
import java.util.UUID

class UserViewModel : ViewModel() {

    val firebaseDb = Firebase.firestore
    private val fdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    val threadRef: DatabaseReference = fdb.getReference("threads")
    val userRef: DatabaseReference = fdb.getReference("users")

    private val _threads = MutableLiveData(listOf<ThreadModel>())
    val threads: LiveData<List<ThreadModel>> get() = _threads

    private val _followerList = MutableLiveData(listOf<String>())
    val followerList: LiveData<List<String>> get() = _followerList

    private val _followingList = MutableLiveData(listOf<String>())
    val followingList: LiveData<List<String>> get() = _followingList

    private val _users = MutableLiveData(UserModel())
    val users: LiveData<UserModel> get() = _users

    fun fetchUser(uid: String) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                _users.postValue(user)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun fetchThreads(uid: String) {
        threadRef.orderByChild("userId").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun followUser(userId: String, currentUserId: String) {
        val ref = firebaseDb.collection("following").document(currentUserId)
        val followerRef = firebaseDb.collection("follower").document(userId)

        ref.update("followingIds", FieldValue.arrayUnion(userId))
        followerRef.update("followerIds", FieldValue.arrayUnion(currentUserId))
    }

    fun getFollowers(userId: String) {
        firebaseDb.collection("follower").document(userId)
            .addSnapshotListener { value, error ->
                val followerIds = value?.get("followerIds") as? List<String> ?: listOf()
                _followerList.postValue(followerIds)
            }
    }

    fun getFollowing(userId: String) {
        firebaseDb.collection("following").document(userId)
            .addSnapshotListener { value, error ->
                val followingIds = value?.get("followingIds") as? List<String> ?: listOf()
                _followingList.postValue(followingIds)
            }
    }
}