package com.sindorim.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.sindorim.composetest.navigation.MyNavigation
import com.sindorim.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    @ExperimentalPermissionsApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                ComposeTestApp()
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun ComposeTestApp() {
//    var text by rememberSaveable { mutableStateOf("") }
//    var active by rememberSaveable { mutableStateOf(false) }
    var text = rememberSaveable { mutableStateOf("") }
    var active = rememberSaveable { mutableStateOf(false) }


    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyNavigation()
//            val navController = NavController(LocalContext.current)
//            AutoCompleteScreen(navController = navController)

//            Box(Modifier.fillMaxSize()) {
//                // Talkback focus order sorts based on x and y position before considering z-index. The
//                // extra Box with semantics and fillMaxWidth is a workaround to get the search bar to focus
//                // before the content.
//                Box(
//                    Modifier
//                        .semantics { isContainer = true }
//                        .zIndex(1f)
//                        .fillMaxWidth()
//                        .shadow(
//                            elevation = 8.dp,
//                            shape = RoundedCornerShape(14.dp),
//                            clip = false,
//                            ambientColor = Color.LightGray,
//                            spotColor = Color.DarkGray
//                        )
//                ) {
//                    DockedSearchBar(
//                        modifier = Modifier
//                            .align(Alignment.TopCenter)
//                            .padding(top = 8.dp),
//                        query = text.value,
//                        onQueryChange = { text.value = it },
//                        onSearch = { active.value = false },
//                        active = active.value,
//                        onActiveChange = { active.value = it },
//                        colors = SearchBarDefaults.colors(
//                            containerColor = Color.LightGray,
//                            dividerColor = Color.Black,
//                            inputFieldColors = TextFieldDefaults.colors(
//
//                            )
//                        ),
//                        placeholder = { Text("Hinted search text") },
//                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
//                        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
//                        shape = SearchBarDefaults.dockedShape
//                    ) {
//                        LazyColumn(
//                            modifier = Modifier.fillMaxWidth(),
//                            contentPadding = PaddingValues(16.dp),
//                            verticalArrangement = Arrangement.spacedBy(4.dp)
//                        ) {
//                            items(4) { idx ->
//                                val resultText = "Suggestion $idx"
//                                ListItem(
//                                    headlineContent = { Text(resultText) },
//                                    supportingContent = { Text("Additional info") },
//                                    leadingContent = {
//                                        Icon(
//                                            Icons.Filled.Star,
//                                            contentDescription = null
//                                        )
//                                    },
//                                    modifier = Modifier.clickable {
//                                        text.value = resultText
//                                        active.value = false
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//
//                LazyColumn(
//                    contentPadding = PaddingValues(
//                        start = 16.dp,
//                        top = 72.dp,
//                        end = 16.dp,
//                        bottom = 16.dp
//                    ),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    val list = List(100) { "Text $it" }
//                    items(count = list.size) {
//                        Text(
//                            list[it],
//                            Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp)
//                        )
//                    }
//                }
//            }

        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun DefaultPreview() {
    ComposeTestTheme {

    }
}

