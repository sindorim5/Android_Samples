package com.sindorim.composetest.screens.autocomplete

import android.widget.AutoCompleteTextView
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.zIndex
import com.sindorim.composetest.screens.search.InputField

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun AutoCompleteSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = SearchBarDefaults.dockedShape,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardActions: KeyboardActions,
    content: @Composable ColumnScope.() -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Surface(
        shape = shape,
        modifier = modifier.zIndex(1f)
    ) {
        Column {
            InputField(
                value = value,
                placeholder = placeholder,
                onValueChanged = onValueChange,
                onActiveChange = onActiveChange,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                interactionSource = interactionSource,
                keyboardActions = keyboardActions
            )
        }
    }


}