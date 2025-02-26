package com.surgery.procedures_list.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.surgery.MyApplicationTheme
import com.surgery.R
import com.surgery.domain.model.Procedure
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ProcedureGridCard(
    procedure: Procedure,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = procedure.icon,
                contentDescription = stringResource(R.string.content_description_procedure_image),
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = procedure.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
            Text(
                text = stringResource(R.string.phases_count, procedure.phasesCount),
                style = MaterialTheme.typography.bodySmall,
            )

            IconButton(onClick = onFavoriteToggle) {
                Icon(
                    imageVector = if (procedure.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.content_description_favorite),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProcedureCardPreviewFavorite() {
    MyApplicationTheme {
        ProcedureGridCard(
            procedure = Procedure(
                uuid = "UUID",
                name = "Cemented Hip Cup",
                icon = "https://content-staging.touchsurgery.com/5d/21/5d2178b3c0c2ed2d61197bc3f7f8c3940747af70e99ab51c2058fb55fe12698c?Expires=1740911207&Signature=I-PUHQS5u0rkYzjU0Q71E3gMAN59VEuzOFYmibab2Jg03bon5w0Uiw5ApcbflqkJSLWh2nMK19yhwEvE~Kow7bs0Hjp3ViRm4ndO2yVLUiz4Z4zDyq7q2HUM~jLybO-nL6B2PRq4aQh~~UoaICXOzHLqGKVOOJK2i3fcBtDSHUAWv3LpNZs7i8gH8Q3I-BZmeq4I0KTRKWb4k1ekfQQRkdUHD6ISzEcweI9CmeykwR2EA8BePaapvKBB~VmliphOhpJUrBw81wBfD~WB-By8F7k7cHyJJPYZu01y83VajbwFiBiGGjLgtEoyKHJzLQBeNUrYgmvyw76vCNXZm~ZR~Q__&Key-Pair-Id=KNNS9X5VSGQAG",
                phasesCount = 2,
                isFavorite = true,
                creationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                duration = 0,
                phases = null,
            ),
            onClick = {},
            onFavoriteToggle = {},
        )
    }
}

@Preview
@Composable
private fun ProcedureCardPreviewNotFavorite() {
    MyApplicationTheme {
        ProcedureGridCard(
            procedure = Procedure(
                uuid = "UUID",
                name = "Cemented Hip Cup",
                icon = "https://content-staging.touchsurgery.com/5d/21/5d2178b3c0c2ed2d61197bc3f7f8c3940747af70e99ab51c2058fb55fe12698c?Expires=1740911207&Signature=I-PUHQS5u0rkYzjU0Q71E3gMAN59VEuzOFYmibab2Jg03bon5w0Uiw5ApcbflqkJSLWh2nMK19yhwEvE~Kow7bs0Hjp3ViRm4ndO2yVLUiz4Z4zDyq7q2HUM~jLybO-nL6B2PRq4aQh~~UoaICXOzHLqGKVOOJK2i3fcBtDSHUAWv3LpNZs7i8gH8Q3I-BZmeq4I0KTRKWb4k1ekfQQRkdUHD6ISzEcweI9CmeykwR2EA8BePaapvKBB~VmliphOhpJUrBw81wBfD~WB-By8F7k7cHyJJPYZu01y83VajbwFiBiGGjLgtEoyKHJzLQBeNUrYgmvyw76vCNXZm~ZR~Q__&Key-Pair-Id=KNNS9X5VSGQAG",
                phasesCount = 1,
                isFavorite = false,
                creationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                duration = 0,
                phases = null,
            ),
            onClick = {},
            onFavoriteToggle = {},
        )
    }
}