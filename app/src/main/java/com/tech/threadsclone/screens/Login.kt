package com.tech.threadsclone.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tech.threadsclone.navigation.Routes
import com.tech.threadsclone.viewModel.AuthViewModel
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(loginNavHostController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            loginNavHostController.navigate(Routes.BottomNav.routes) {
                popUpTo(loginNavHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(error) {
        if (firebaseUser != null) {
            loginNavHostController.navigate(Routes.BottomNav.routes) {
                popUpTo(loginNavHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    error.let {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }

    var email: String by remember {
        mutableStateOf("")
    }

    var password: String by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Login", style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
        )

        Box(modifier = Modifier.height(50.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = {
            Text(text = "Email")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = {
            Text(text = "Password")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(16.dp))

        ElevatedButton(onClick = {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill all details", Toast.LENGTH_LONG).show()
            } else {
                authViewModel.login(email, password, context)
            }

        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Login", style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.Black
                ), modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Box(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            loginNavHostController.navigate(Routes.Register.routes) {
                popUpTo(loginNavHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }) {
            Text(
                text = "New User? Create Account", style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
    }
}
