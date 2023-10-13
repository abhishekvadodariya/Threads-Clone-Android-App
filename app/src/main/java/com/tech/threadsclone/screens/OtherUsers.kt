package com.tech.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.tech.threadsclone.itemView.ThreadItem
import com.tech.threadsclone.model.UserModel
import com.tech.threadsclone.navigation.Routes
import com.tech.threadsclone.utils.SharedPrefrence
import com.tech.threadsclone.viewModel.AuthViewModel
import com.tech.threadsclone.viewModel.UserViewModel


@Composable
fun OtherUsers(otherUserNavHostController: NavHostController, uid: String) {


    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val users by userViewModel.users.observeAsState(null)

    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    userViewModel.fetchThreads(uid)
    userViewModel.fetchUser(uid)

    userViewModel.getFollowers(uid)
    userViewModel.getFollowing(uid)

    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null) {
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            otherUserNavHostController.navigate(Routes.Login.routes) {
                popUpTo(otherUserNavHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    LazyColumn() {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                val (text, logo, userName, bio, followers, following, logoutBtn) = createRefs()

                Text(
                    text = users!!.name, style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Image(
                    painter = rememberAsyncImagePainter(users!!.imageUrl),
                    contentDescription = "profile Picture",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.end)
                        }
                        .size(120.dp)
                        .clip(CircleShape), contentScale = ContentScale.Crop
                )

                Text(
                    text = users!!.userName, style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(userName) {
                        top.linkTo(text.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = users!!.bio, style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(bio) {
                        top.linkTo(userName.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followerList!!.size} Followers", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(followers) {
                        top.linkTo(bio.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followingList!!.size} Following", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(following) {
                        top.linkTo(followers.top)
                        start.linkTo(parent.start)
                    }
                )

                ElevatedButton(onClick = {
                    if (currentUserId != "") {
                        userViewModel.followUser(uid, currentUserId)
                    }

                }, modifier = Modifier.constrainAs(logoutBtn) {
                    top.linkTo(following.bottom)
                    start.linkTo(parent.start)
                }) {
                    Text(
                        text = if (followerList != null && followerList!!.isNotEmpty() && followerList!!.contains(
                                currentUserId
                            )
                        ) "Following" else "Follow"
                    )
                }
            }
        }

        if (threads != null && users != null) {
            items(threads ?: emptyList()) { pair ->
                ThreadItem(
                    thread = pair,
                    users = users!!,
                    threadItemNavHostController = otherUserNavHostController,
                    userId = FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }
    }
}