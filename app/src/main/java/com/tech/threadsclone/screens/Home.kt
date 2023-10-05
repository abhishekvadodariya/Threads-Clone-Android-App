package com.tech.threadsclone.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tech.threadsclone.itemView.ThreadItem
import com.tech.threadsclone.viewModel.HomeViewModel

@Composable
fun Home() {
    val context = LocalContext.current
    val homeViewModel:HomeViewModel = viewModel()
    val threadAndUser by homeViewModel.threadsAndUsers.observeAsState(null)

    LazyColumn {
        /*items(threadAndUser?: emptyList<>()) {pairs ->

            ThreadItem()
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun ShowHome() {
    Home()
}