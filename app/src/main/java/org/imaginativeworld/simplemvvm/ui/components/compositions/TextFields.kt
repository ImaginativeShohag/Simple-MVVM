package org.imaginativeworld.simplemvvm.ui.components.compositions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.ui.theme.AppTheme
import org.imaginativeworld.simplemvvm.ui.theme.Fonts

fun defaultTextInputShape() = RoundedCornerShape(8.dp)

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
private fun DefaultTextInputFieldPreview() {
    AppTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            DefaultTextInputField(
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "The quick brown fox jumps over a lazy dog, and the quick black cat jumps over a lazy tiger.",
                keyboardType = KeyboardType.Text,
            )

            DefaultTextInputField(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(128.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
                placeholder = "The quick brown fox jumps over a lazy dog, and the quick black cat jumps over a lazy tiger.",
                keyboardType = KeyboardType.Text,
                singleLine = false,
            )

            DefaultPasswordInputField(
                modifier = Modifier.padding(top = 32.dp),
                textFieldValue = remember { mutableStateOf(TextFieldValue()) },
//                placeholder = "I am  a placeholder",
            )
        }
    }
}

@Composable
fun DefaultTextInputField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    background: Color = Color(0xFFF1F3F2),
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    fontSize: TextUnit = 15.sp,
    height: Dp = 48.dp,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    val rModifier = remember { modifier }
    val rBackground = remember { background }
    val rPlaceholder = remember { placeholder }
    val rKeyboardType = remember { keyboardType }
    val rReadOnly = remember { readOnly }
    val rSingleLine = remember { singleLine }
    val rImeAction = remember { imeAction }
    val rKeyboardActions = remember { keyboardActions }
    val rFontSize = remember { fontSize }
    val rHeight = remember { height }
    val rOnValueChange = remember { onValueChange }

    BasicTextField(
        value = textFieldValue.value,
        singleLine = rSingleLine,
        textStyle = TextStyle(
            fontSize = rFontSize,
            fontFamily = Fonts.TitilliumWeb
        ),
        onValueChange = {
            textFieldValue.value = it

            rOnValueChange(it)
        },
        keyboardActions = rKeyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Next) },
            onSearch = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = rKeyboardType,
            imeAction = rImeAction
        ),
        modifier = rModifier
            .fillMaxWidth()
            .clickable { },
        readOnly = rReadOnly,
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .clip(defaultTextInputShape())
                    .background(rBackground)
                    .height(rHeight)
                    .padding(horizontal = 12.dp),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 13.dp, bottom = 15.dp)
                ) {
                    innerTextField()

                    if (textFieldValue.value.text.isEmpty()) {
                        Text(
                            text = rPlaceholder,
                            color = MaterialTheme.colors.onBackground.copy(.35f),
                            fontSize = rFontSize,
                            fontFamily = Fonts.TitilliumWeb,
                            maxLines = if (rSingleLine) 1 else Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
            }
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun DefaultPasswordInputField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<TextFieldValue>,
    placeholder: String = "●●●●●●",
    readOnly: Boolean = false,
    fontSize: TextUnit = 15.sp,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions? = null,
    onValueChange: (TextFieldValue) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val passwordVisibility = remember { mutableStateOf(false) }

    val rModifier = remember { modifier }
    val rPlaceholder = remember { placeholder }
    val rReadOnly = remember { readOnly }
    val rFontSize = remember { fontSize }
    val rImeAction = remember { imeAction }
    val rKeyboardActions = remember { keyboardActions }
    val rOnValueChange = remember { onValueChange }

    BasicTextField(
        value = textFieldValue.value,
        singleLine = true,
        visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(mask = '●'),
        onValueChange = {
            textFieldValue.value = it

            rOnValueChange(it)
        },
        keyboardActions = rKeyboardActions ?: KeyboardActions(
            onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Next) }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = rImeAction
        ),
        modifier = rModifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 0.dp),
        readOnly = rReadOnly,
        textStyle = TextStyle(
            fontSize = rFontSize,
            fontFamily = Fonts.TitilliumWeb
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .clip(defaultTextInputShape())
                    .background(Color(0xFFF1F3F2))
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(start = 12.dp, top = 12.dp, bottom = 13.dp)
                ) {
                    innerTextField()

                    if (textFieldValue.value.text.isEmpty()) {
                        Text(
                            text = rPlaceholder,
                            color = MaterialTheme.colors.onBackground.copy(.35f),
                            fontSize = rFontSize,
                            fontFamily = Fonts.TitilliumWeb
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                    AnimatedVisibility(
                        visible = passwordVisibility.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_visibility_24),
                            "Show Password"
                        )
                    }

                    AnimatedVisibility(
                        visible = !passwordVisibility.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_visibility_off_24),
                            "Hide Password"
                        )
                    }
                }

            }
        }
    )
}

// ================================================================
// Label
// ================================================================

@Preview
@Composable
fun LabelPreview() {
    AppTheme {
        Label(
            text = "I Am A Caption"
        )
    }
}

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 15.sp,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    Text(
        text = text,
        fontSize = fontSize,
        modifier = modifier,
        fontWeight = fontWeight,
        lineHeight = 18.sp,
    )
}

@Preview
@Composable
fun TitlePreview() {
    AppTheme {
        Label(
            text = "I Am A Title"
        )
    }
}

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.h1,
        modifier = modifier
    )
}