package com.sindorim.jetareader.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.sindorim.jetareader.R
import com.sindorim.jetareader.components.FABContent
import com.sindorim.jetareader.components.ReaderAppBar
import com.sindorim.jetareader.components.TitleSection
import com.sindorim.jetareader.model.MBook
import com.sindorim.jetareader.navigation.ReaderScreens

@Composable
fun ReaderHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = {
            FABContent {

            }
        },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeContent(navController)
        }
    }
} // End of ReaderHomeScreen

@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current)) {

    val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    } else {
        "N/A"
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.align(alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleSection(label = "Your reading\n" + "activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant
                ) // End of Profile Icon
                Text(
                    text = currentUserName.toString(),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                ) // End of Name Text
                Divider()
            }

        } // End of Title Row
    } // End of User Column
} // End of HomeContent

@Preview
@Composable
fun ListCard(
    book: MBook = MBook(
        id = "0",
        title = "Atomic Kotlin",
        authors = "sindorim",
        notes = "King of Kotlin"
    ),
    onPressed: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        shape = RoundedCornerShape(29.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable {
                onPressed.invoke(book.title.toString())
            }
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberAsyncImagePainter(model = ""),
                    contentDescription = "Book Image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                ) // End of Book Image
                Spacer(modifier = Modifier.width(50.dp))

                Column() {

                }
            }
        }
    } // End of Card
} // End of ListCard

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {


} // End of ReadingRightNowArea