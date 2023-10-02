package com.tech.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
import io.grpc.okhttp.internal.Util

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
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                })

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
    }
}

@Composable
fun BasicTextFieldWithHint(
    hint: String,
    value: String,
    onValueChnage: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(text = hint, color = Color.Gray)
        }

        /*BasicTextField(
            value = value,
            onValueChnage = onValueChnage,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = modifier.fillMaxWidth()
        ) {

        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun AddPostView() {
    AddThreads()
}