package com.example.movieapplicationcompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieapplicationcompose.R
import com.example.movieapplicationcompose.data.database.entities.FavoriteMovie
import com.example.movieapplicationcompose.data.models.Details
import com.example.movieapplicationcompose.viewModel.MovieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavHostController, id: Int) {
    val movieViewModel: MovieViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        movieViewModel.id = id
        movieViewModel.getDetailsById()
    }

    val state = movieViewModel.state
    val details = state.detailsData
    val isFavorite by remember { mutableStateOf(movieViewModel.isMovieFavorite(id)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = details.title, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("Home screen") {
                            popUpTo("Home screen") { inclusive = true }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isFavorite) {
                            movieViewModel.removeFavoriteMovie(id)
                        } else {
                            movieViewModel.addFavoriteMovie(
                                FavoriteMovie(
                                    id = details.id,
                                    title = details.title,
                                    poster = details.poster,
                                    plot = details.plot,
                                    imdbRating = details.imdb_rating
                                )
                            )
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                BackGroundPoster(details = details)
                ForegroundPoster(details = details)
                Column(
                    Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                        .verticalScroll(rememberScrollState())
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = details.title,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 38.sp,
                        color = Color.White,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )
                    Rating(details = details, modifier = Modifier)
                    TextBuilder(icon = Icons.Filled.Info, title = "Summery:", bodyText = details.plot)
                    TextBuilder(icon = Icons.Filled.Person, title = "Actors:", bodyText = details.actors)
                    ImageRow(details = details)
                }
            }
        }
    )
}

@Composable
fun ImageRow(details: Details) {
    if (details.images.isNotEmpty()) {
        LazyRow {
            items(details.images.size) {
                AsyncImage(
                    model = details.images[it], contentDescription = "",
                    Modifier
                        .padding(6.dp)
                        .height(70.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun TextBuilder(icon: ImageVector, title: String, bodyText: String) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = "Person",
            tint = Color.White
        )
        Text(
            text = title,
            Modifier.padding(start = 10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
    Text(text = bodyText, color = Color.White)
}

@Composable
fun Rating(details: Details, modifier: Modifier) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Icon(imageVector = Icons.Filled.Star, contentDescription = "", tint = Color.White)
        Text(
            text = details.imdb_rating,
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = modifier.width(25.dp))
        Icon(
            painter = painterResource(id = R.drawable.time_24),
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = details.runtime,
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = modifier.width(25.dp))
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "", tint = Color.White)
        Text(
            text = details.released,
            modifier.padding(start = 6.dp),
            color = Color.White
        )
    }
}

@Composable
fun ForegroundPoster(details: Details) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
            .padding(top = 80.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            model = details.poster, contentDescription = details.title,
            Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .width(250.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color(0xB91A1B1B),
                        )
                    ), shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun BackGroundPoster(details: Details) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        AsyncImage(
            model = details.poster, contentDescription = details.title,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.6f)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.DarkGray
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}
