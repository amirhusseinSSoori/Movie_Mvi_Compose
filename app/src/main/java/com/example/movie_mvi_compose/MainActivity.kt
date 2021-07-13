package com.example.movie_mvi_compose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movie_mvi_compose.data.network.errorHandling.Resource
import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.example.movie_mvi_compose.ui.theme.Movie_Mvi_ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Movie_Mvi_ComposeTheme {

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {









                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    var scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        val coroutineScope = rememberCoroutineScope()
        val state = mutableStateOf<List<MovieItem>>(listOf())
        val viewmodel: MovieViewModel = viewModel()
           coroutineScope.launch {
            viewmodel.getDetailsCollect.collect { data ->
                when (data) {
                    is Resource.Success -> {
                        Log.e("Data", "Success: ${data.data}")
                        state.value = data.data!!
                    }
                    is Resource.ApiError -> {
                        Log.e("Data", "Failure: ${data.message}")
                        state.value = data.data!!
                    }
                    is Resource.UnknownError -> {
                        Log.e("Data", "Failure: ${data.message}")
                        state.value = data.data!!
                    }
                }
            }
        }
        viewmodel.showAllMovie()
        Greeting(name = state.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Movie_Mvi_ComposeTheme {
        Greeting("Android")
    }
}