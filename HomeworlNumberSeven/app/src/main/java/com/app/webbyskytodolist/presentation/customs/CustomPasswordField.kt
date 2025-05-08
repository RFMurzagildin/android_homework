package com.app.webbyskytodolist.presentation.customs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.webbyskytodolist.R
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor
import com.app.webbyskytodolist.presentation.ui.theme.MainTextFieldColor
import com.app.webbyskytodolist.presentation.ui.theme.TextGrayColor

@Composable
fun CustomPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

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
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    painter = painterResource(
                        id = if (isPasswordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                    ),
                    contentDescription = "Toggle Password Visibility"
                )
            }
        },
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
        singleLine = true,
    )
}