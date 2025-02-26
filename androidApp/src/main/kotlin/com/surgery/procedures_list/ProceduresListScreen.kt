package com.surgery.procedures_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.surgery.Configurations
import com.surgery.MyApplicationTheme
import com.surgery.domain.model.Procedure
import com.surgery.procedures_list.comp.ProcedureGridCard
import com.surgery.procedures_list.comp.ProcedureListCard
import com.surgery.view_model.ProceduresListContract
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ProceduresListScreen(
    searchText: String,
    proceduresList: List<Procedure>,
    onSearchUpdated: (String) -> Unit,
    onItemClick: (Procedure) -> Unit,
    onToggleFavorite: (Procedure) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { onSearchUpdated(it) },
            label = { Text(text = "Search") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    onSearchUpdated("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (Configurations.IS_GRID) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = proceduresList, key = { it.uuid }) { procedure ->
                    ProcedureGridCard(
                        procedure = procedure,
                        onClick = {
                            onItemClick(procedure)
                        },
                        onFavoriteToggle = {
                            onToggleFavorite(procedure)
                        },
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier) {
                items(items = proceduresList, key = { it.uuid }) { procedure ->
                    ProcedureListCard(
                        procedure = procedure,
                        onClick = {
                            onItemClick(procedure)
                        },
                        onFavoriteToggle = {
                            onToggleFavorite(procedure)
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProceduresListScreenPreview() {
    MyApplicationTheme {
        ProceduresListScreen(
            searchText = "Search",
            proceduresList = listOf(
                Procedure(
                    uuid = "UUID1",
                    name = "Cemented Hip Cup",
                    icon = "https://content-staging.touchsurgery.com/5d/21/5d2178b3c0c2ed2d61197bc3f7f8c3940747af70e99ab51c2058fb55fe12698c?Expires=1740911207&Signature=I-PUHQS5u0rkYzjU0Q71E3gMAN59VEuzOFYmibab2Jg03bon5w0Uiw5ApcbflqkJSLWh2nMK19yhwEvE~Kow7bs0Hjp3ViRm4ndO2yVLUiz4Z4zDyq7q2HUM~jLybO-nL6B2PRq4aQh~~UoaICXOzHLqGKVOOJK2i3fcBtDSHUAWv3LpNZs7i8gH8Q3I-BZmeq4I0KTRKWb4k1ekfQQRkdUHD6ISzEcweI9CmeykwR2EA8BePaapvKBB~VmliphOhpJUrBw81wBfD~WB-By8F7k7cHyJJPYZu01y83VajbwFiBiGGjLgtEoyKHJzLQBeNUrYgmvyw76vCNXZm~ZR~Q__&Key-Pair-Id=KNNS9X5VSGQAG",
                    phasesCount = 2,
                    isFavorite = true,
                    phases = null,
                    duration = 1,
                    creationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                ),
                Procedure(
                    uuid = "UUID2",
                    name = "Cemented Hip Cup",
                    icon = "https://content-staging.touchsurgery.com/5d/21/5d2178b3c0c2ed2d61197bc3f7f8c3940747af70e99ab51c2058fb55fe12698c?Expires=1740911207&Signature=I-PUHQS5u0rkYzjU0Q71E3gMAN59VEuzOFYmibab2Jg03bon5w0Uiw5ApcbflqkJSLWh2nMK19yhwEvE~Kow7bs0Hjp3ViRm4ndO2yVLUiz4Z4zDyq7q2HUM~jLybO-nL6B2PRq4aQh~~UoaICXOzHLqGKVOOJK2i3fcBtDSHUAWv3LpNZs7i8gH8Q3I-BZmeq4I0KTRKWb4k1ekfQQRkdUHD6ISzEcweI9CmeykwR2EA8BePaapvKBB~VmliphOhpJUrBw81wBfD~WB-By8F7k7cHyJJPYZu01y83VajbwFiBiGGjLgtEoyKHJzLQBeNUrYgmvyw76vCNXZm~ZR~Q__&Key-Pair-Id=KNNS9X5VSGQAG",
                    phasesCount = 1,
                    isFavorite = false,
                    phases = null,
                    duration = 1,
                    creationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                )
            ),
            onSearchUpdated = {},
            onItemClick = {},
            onToggleFavorite = {},
        )
    }
}