package com.example.crossloqui.ui.conversation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.crossloqui.ui.theme.CrossLoquiTheme

@Composable
fun ConversationInput() {

    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("Text"))
    }

    TextField(
        leadingIcon = {
                      Icon(
                          imageVector = Icons.Filled.KeyboardArrowUp,
                          contentDescription = "More Options"
                      )
        },
        trailingIcon = {
                      Icon(
                          imageVector = Icons.Filled.Send,
                          contentDescription = "Send"
                      )
        },
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it}
    )
}


@Preview
@Composable
fun ConversationInputPreview() {
    CrossLoquiTheme {
        ConversationInput()
    }
}