package com.example.movieapplicationcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapplicationcompose.data.models.Details
import com.example.movieapplicationcompose.viewModel.MovieViewModel

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val movieViewModel: MovieViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        movieViewModel.getAllFavoriteMovies()
    }

    val favoriteMovies = movieViewModel.state.isMovieFavorite
    val favoriteMovieDetails = remember { mutableStateListOf<Details>() }

    LaunchedEffect(favoriteMovies) {
        favoriteMovieDetails.clear()

        favoriteMovies.forEach { movieId ->
            movieViewModel.getFavoritesById(movieId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.1f))
    ) {

        TopBar(title = "Favorite Movies")

        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn {
            items(movieViewModel.state.detailsDataList.distinct()) { movie ->
                FavoriteMovieItem(
                    movie = movie,
                    isFavorite = true,
                    onFavoriteClick = {
                        movieViewModel.removeFavoriteMovie(movie.id)
                        movieViewModel.state.detailsDataList.remove(movie)
                    },
                    onMovieClick = {
                        navController.navigate("Details screen/${movie.id}") {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movie: Details,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onMovieClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onMovieClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5).copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.poster),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Text(
                    text = "Year: ${movie.year}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = "IMDb: ${movie.imdb_rating}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(.4f)
        )
    )
}
