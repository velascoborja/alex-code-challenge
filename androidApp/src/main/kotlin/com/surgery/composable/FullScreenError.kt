package com.surgery.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.surgery.R
import com.surgery.state.FullScreenLoadingManager

@Composable
fun FullScreenError(
    onRetryClick: (() -> Unit)? = null,
) {
    val error by FullScreenLoadingManager.error

    error.takeIf { !it.isNullOrEmpty() }?.let {
        AlertDialog(
            onDismissRequest = {
                FullScreenLoadingManager.hideError()
            },
            title = {
                Text(text = stringResource(R.string.something_went_wrong))
            },
            text = {
                Text(it)
            },
            confirmButton = {
                onRetryClick?.let {
                    Button(
                        onClick = {
                            FullScreenLoadingManager.hideError()
                            onRetryClick()
                        }
                    ) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        FullScreenLoadingManager.hideError()
                    }) {
                    Text(text = stringResource(R.string.close))
                }
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                decorFitsSystemWindows = false,
            )
        )
    }
}