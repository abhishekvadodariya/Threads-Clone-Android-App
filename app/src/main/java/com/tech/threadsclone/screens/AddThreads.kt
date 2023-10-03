package com.tech.threadsclone.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.tech.threadsclone.R
import com.tech.threadsclone.navigation.Routes
import com.tech.threadsclone.utils.SharedPrefrence
import io.grpc.okhttp.internal.Util

@Composable
fun AddThreads() {

    val context = LocalContext.current

    var thread by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val permissionToRequest = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    val permissionlauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {

                isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val (crossPic, text, logo, userName, editText, attachMedia, replayText, button, imageBox) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Close",
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {

                })
        Text(
            text = "Add Thread", style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black
            ), modifier = Modifier.constrainAs(text) {
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 12.dp)
                bottom.linkTo(crossPic.bottom)
            }
        )

        Image(
            painter = rememberAsyncImagePainter(model = SharedPrefrence.getImageUrl(context)),
            contentDescription = "logo",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                .size(36.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop
        )

        Text(
            text = SharedPrefrence.getUserName(context), style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            ), modifier = Modifier.constrainAs(userName) {
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
        )

        BasicTextFieldWithHint(
            hint = "Start new Thread ...",
            value = thread,
            onValueChange = { thread = it },
            modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(userName.bottom)
                    start.linkTo(userName.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
        )

        if (imageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.baseline_attachment_24),
                contentDescription = "Close",
                modifier = Modifier
                    .constrainAs(attachMedia) {
                        top.linkTo(editText.top)
                        start.linkTo(editText.start)
                    }
                    .clickable {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionlauncher.launch(permissionToRequest)
                        }
                    })
        } else {
            Box(modifier = Modifier
                .background(color = Color.Gray)
                .padding(12.dp)
                .constrainAs(imageBox) {
                    top.linkTo(editText.bottom)
                    start.linkTo(editText.start)
                    end.linkTo(parent.end)
                }.height(250.dp))
        }
    }
}

@Composable
fun BasicTextFieldWithHint(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(text = hint, color = Color.Gray)
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddPostView() {
    AddThreads()
}