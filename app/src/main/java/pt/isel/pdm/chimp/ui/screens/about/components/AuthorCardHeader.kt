package pt.isel.pdm.chimp.ui.screens.about.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun AuthorCardHeader(
    name: String,
    number: String,
    image: Painter,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            modifier =
                Modifier
                    .scale(0.8f)
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        )
        Text(
            text = number,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp),
        )
        Image(
            painter = image,
            contentDescription = null,
            modifier =
                Modifier
                    .padding(12.dp)
                    .clip(CircleShape)
                    .size(150.dp),
        )
    }
}
