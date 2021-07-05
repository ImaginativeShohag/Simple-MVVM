package org.imaginativeworld.simplemvvm.ui.components.compositions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.ui.theme.AppTheme
import org.imaginativeworld.simplemvvm.ui.theme.Fonts

@Preview
@Composable
fun CustomAppBarPreview() {
    AppTheme {
        CustomAppBar(title = stringResource(id = R.string.app_name))
    }
}

@Composable
fun CustomAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClicked: () -> Unit = {},
) {

    Box(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
    ) {
        DefaultIconBorderLessButton(
            modifier = Modifier,
            size = 56.dp,
            padding = 0.dp,
            iconDrawableId = R.drawable.ic_back,
            color = MaterialTheme.colors.onBackground,
            shape = CircleShape,
        ) {
            onBackClicked()
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = title,
            textAlign = TextAlign.Center,
            fontFamily = Fonts.TitilliumWeb,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}