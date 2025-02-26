package com.surgery.procedures_list.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.surgery.MyApplicationTheme
import com.surgery.R
import com.surgery.domain.model.Phase
import com.surgery.domain.model.Procedure
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProcedureDetailBottomSheet(
    modifier: Modifier = Modifier,
    procedure: Procedure,
    onToggleFavorite: (Procedure) -> Unit,
    onDismiss: () -> Unit,
) {
    var isFavorite by remember { mutableStateOf(procedure.isFavorite) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        AsyncImage(
            model = procedure.icon,
            contentDescription = stringResource(R.string.content_description_procedure_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = procedure.name,
                style = MaterialTheme.typography.titleLarge,
            )
            IconButton(onClick = {
                isFavorite = !isFavorite
                onToggleFavorite(procedure)
            }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.content_description_favorite),
                )
            }
        }

        Text(
            text = stringResource(R.string.duration, procedure.duration.toMinutes()),
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = stringResource(R.string.created_on, procedure.creationDate.toFormattedString()),
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = stringResource(R.string.phases),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp),
        )

        procedure.phases?.let { phases ->
            LazyRow(modifier = Modifier.padding(top = 8.dp)) {
                items(items = phases, key = { it.uuid }) { phase ->
                    PhaseItem(phase)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.button_close))
        }
    }
}

@Composable
fun PhaseItem(phase: Phase) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = phase.icon,
            contentDescription = stringResource(R.string.content_description_phase_image),
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = phase.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProcedureDetailBottomSheetPreview() {
    MyApplicationTheme {
        ProcedureDetailBottomSheet(
            procedure = Procedure(
                creationDate = LocalDateTime.parse("2015-04-14T10:00:51.940581"),
                duration = 60,
                icon = "icon",
                name = "Procedure name",
                phasesCount = 2,
                phases = listOf(
                    Phase(
                        icon = "Icon",
                        name = "Phase name",
                        uuid = "uuid1",
                    ),
                    Phase(
                        icon = "Icon",
                        name = "Phase name",
                        uuid = "uuid2",
                    ),
                ),
                uuid = "uuid1",
                isFavorite = true
            ),
            onToggleFavorite = {}
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhaseItemPreview() {
    MyApplicationTheme {
        PhaseItem(
            Phase(
                icon = "Icon",
                name = "Phase name",
                uuid = "uuid",
            )
        )
    }
}

private fun LocalDateTime.toFormattedString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(
        Date(
            toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        )
    )
}

private fun Int.toMinutes(): Int {
    val minutes = this / 60
    return minutes
}