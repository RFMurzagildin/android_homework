package com.app.webbyskytodolist.presentation.customs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.webbyskytodolist.R
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor
import com.app.webbyskytodolist.presentation.ui.theme.MainTextFieldColor
import com.app.webbyskytodolist.presentation.ui.theme.TextGrayColor

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontSize = 16.sp
        ),
        placeholder = {
            Text(
                text = placeholderText,
                color = TextGrayColor,
                fontFamily = FontFamily(Font(R.font.nunito_regular))
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MainTextFieldColor,
            focusedContainerColor = MainDarkColor,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = MainButtonColor,
            unfocusedIndicatorColor = MainDarkColor
        ),
        shape = shape,
        singleLine = true
    )
}
