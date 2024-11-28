package pt.isel.pdm.chimp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.isel.pdm.chimp.infrastructure.services.media.problems.Problem
import pt.isel.pdm.chimp.ui.utils.getMessage

@Composable
fun Errors(errors: List<String>) {
    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        errors.forEach {
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 20.dp),
                text = "• $it",
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
fun ErrorAlert(error: Problem) {
    val message = error.getMessage()
    Box(
        modifier =
            Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(16.dp),
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onErrorContainer),
        )
    }
}
