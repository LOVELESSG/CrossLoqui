package com.example.crossloqui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrossLoquiAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable () -> Unit = {},
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = title,
        scrollBehavior = scrollBehavior,
        actions = actions,
        navigationIcon = navigationIcon,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CrossLoquiAppBarPreview() {
    CrossLoquiTheme {
        CrossLoquiAppBar(title = { Text(text = "AppBar") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CrossLoquiAppBarPreviewDark() {
    CrossLoquiTheme(darkTheme = true) {
        CrossLoquiAppBar(title = { Text(text = "AppBar") })
    }
}