package com.example.movie_mvi_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.movie_mvi_compose.data.network.response.MovieItem
import com.example.movie_mvi_compose.ui.movie.BtnRetry
import com.example.movie_mvi_compose.ui.movie.MovieLazyList
import com.example.movie_mvi_compose.ui.movie.MovieViewModel
import com.example.movie_mvi_compose.ui.theme.Movie_Mvi_ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Movie_Mvi_ComposeTheme {

                Surface(color = MaterialTheme.colors.background) {
                    val viewmodel: MovieViewModel = viewModel()
                    val  data=viewmodel.state.collectAsState()



                    val  error by remember { viewmodel.handelError }


                    if(error.isEmpty()){
                         viewmodel.showAllMovie()
                          MovieLazyList(data.value!!)
                      }else{
                          BtnRetry{
                              viewmodel.showAllMovie()
                          }
                      }





                }
            }
        }
    }
}




@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Movie_Mvi_ComposeTheme {
        Greeting("Android")
    }
}