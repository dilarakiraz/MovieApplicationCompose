package com.example.movieapplicationcompose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationcompose.data.database.entities.FavoriteMovie
import com.example.movieapplicationcompose.data.models.Data
import com.example.movieapplicationcompose.data.models.Details
import com.example.movieapplicationcompose.data.repository.MovieRepository
import com.example.movieapplicationcompose.paging.PaginationFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapplicationcompose.utils.Result

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    var state by mutableStateOf(ScreenState())
    var id by mutableIntStateOf(0)
    var query by mutableStateOf("")

    private val pagination = PaginationFactory(
        initialPage = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            val result = repository.getMovieList(nextPage)
            result
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newPage ->
            state = state.copy(
                movies = state.movies + items,
                page = newPage,
                endReached = state.page == 25
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            pagination.loadNextPage()
        }
    }

    fun getDetailsById() {
        viewModelScope.launch {
            when (val result = repository.getDetailById(id)) {
                is Result.Success -> {
                    state = state.copy(detailsData = result.data)
                }
                is Result.Error -> {
                    state = state.copy(error = result.exception.message)
                }
            }
        }
    }


    fun onSearchQueryChange(newQuery: String) {
        query = newQuery
        searchMovies(query)
    }

    private fun searchMovies(query: String) {
        if (query.isEmpty()) {
            loadInitialMovies()
        } else {
            val filteredMovies = state.movies.filter { movie ->
                movie.title.contains(query, ignoreCase = true)
            }
            state = state.copy(movies = filteredMovies)
        }
    }

    private fun loadInitialMovies() {
        viewModelScope.launch {
            when (val result = repository.getMovieList(state.page)) {
                is Result.Success -> {
                    state = state.copy(movies = result.data)
                }
                is Result.Error -> {
                    state = state.copy(error = result.exception.localizedMessage)
                }
            }
        }
    }

    fun addFavoriteMovie(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.addFavoriteMovie(movie)
        }
    }

    fun removeFavoriteMovie(id: Int) {
        viewModelScope.launch {
            repository.removeFavoriteMovie(id)
        }
    }

    fun isMovieFavorite(id: Int): Boolean {
        var isFavorite = false
        viewModelScope.launch {
            isFavorite = repository.isMovieFavorite(id)
        }
        return isFavorite
    }

    fun getAllFavoriteMovies() {
        viewModelScope.launch {
            val favoriteMovies = repository.getAllFavoriteMovies()
        }
    }
}

data class ScreenState(
    val movies: List<Data> = emptyList(),
    val page: Int = 1,
    val detailsData: Details = Details(),
    val endReached: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)