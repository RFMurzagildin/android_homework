package com.app.webbyskytodolist.presentation.customs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.webbyskytodolist.R
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor

@Composable
fun CustomButton(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(horizontal = 33.dp)
        .padding(bottom = 8.dp),
    text: String,
    textColor: Color = MainDarkColor,
    fontFamily: FontFamily = FontFamily(Font(R.font.nunito_bold)),
    action: () -> Unit,
) {
    Button(
        onClick = { action() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MainButtonColor,
            contentColor = MainDarkColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamily
            )
        )
    }
}