package com.tech.threadsclone.screens

import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tech.threadsclone.R
import com.tech.threadsclone.itemView.ThreadItem
import com.tech.threadsclone.model.UserModel
import com.tech.threadsclone.navigation.Routes
import com.tech.threadsclone.utils.SharedPrefrence
import com.tech.threadsclone.viewModel.AuthViewModel
import com.tech.threadsclone.viewModel.UserViewModel

@Composable
fun Profile(profileNavHostController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null) {
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }

    if (currentUserId != null) {
        userViewModel.getFollowers(currentUserId)
        userViewModel.getFollowing(currentUserId)
    }

    val user = UserModel(
        name = SharedPrefrence.getName(context),
        userName = SharedPrefrence.getUserName(context),
        imageUrl = SharedPrefrence.getImageUrl(context)
    )

    if (firebaseUser != null) {
        userViewModel.fetchThreads(firebaseUser!!.uid)
    }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            profileNavHostController.navigate(Routes.Login.routes) {
                popUpTo(profileNavHostController.graph.startDestinationId)
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
                    text = SharedPrefrence.getName(context), style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Image(
                    painter = rememberAsyncImagePainter(model = SharedPrefrence.getImageUrl(context)),
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
                    text = SharedPrefrence.getUserName(context), style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    ), modifier = Modifier.constrainAs(userName) {
                        top.linkTo(text.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = SharedPrefrence.getBio(context), style = TextStyle(
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
                    authViewModel.logout()
                }, modifier = Modifier.constrainAs(logoutBtn) {
                    top.linkTo(following.bottom)
                    start.linkTo(parent.start)
                }) {
                    Text(text = "Logout")
                }
            }
        }
        items(threads ?: emptyList()) { pair ->
            ThreadItem(
                thread = pair,
                users = user,
                threadItemNavHostController = profileNavHostController,
                userId = FirebaseAuth.getInstance().currentUser!!.uid
            )
        }
    }
}