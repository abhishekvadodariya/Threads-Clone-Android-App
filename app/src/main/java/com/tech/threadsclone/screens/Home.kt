package com.tech.threadsclone.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.tech.threadsclone.itemView.ThreadItem
import com.tech.threadsclone.viewModel.HomeViewModel
import kotlin.concurrent.thread
import kotlin.time.Duration.Companion.seconds

@Composable
fun Home(homeNavHostController: NavHostController) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel()
    val threadAndUser by homeViewModel.threadsAndUsers.observeAsState(null)

    LazyColumn {
        items(threadAndUser ?: emptyList()) { pairs ->

            ThreadItem(
                thread = pairs.first,
                users = pairs.second,
                homeNavHostController,
                FirebaseAuth.getInstance().currentUser!!.uid
            )
        }
    }
}