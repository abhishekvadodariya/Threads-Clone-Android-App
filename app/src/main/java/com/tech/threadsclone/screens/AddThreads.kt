package com.tech.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.tech.threadsclone.R
import com.tech.threadsclone.utils.SharedPrefrence

@Composable
fun AddThreads() {

    val context = LocalContext.current

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
                .constrainAs(crossPic) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                })

        Text(
            text = "userName", style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black
            ), modifier = Modifier.constrainAs(userName) {
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 12.dp)
                bottom.linkTo(crossPic.bottom)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddPostView() {
    AddThreads()
}