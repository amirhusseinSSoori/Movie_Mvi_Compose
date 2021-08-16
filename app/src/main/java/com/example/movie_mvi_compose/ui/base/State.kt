package com.example.movie_mvi_compose.ui.base

import retrofit2.Response


interface UiState

interface UiEvent

interface UiEffect

sealed class DataState<T> {

    //show Error
    data class Response<T>(
        val uiComponent: UIComponent
    ): DataState<T>()

    data class Loading<T>(
        val data: T ?=null
    ): DataState<T>()
    //get from Server
    data class Data<T>(
        val data: T
    ): DataState<T>()


    //insert to DataBase and get Error
    data class DataBase<T>(
        val data: T,
        val message:String
    ): DataState<T>()



}
sealed class UIComponent{
    data class ErrorConnection(
        val message: String,
        ): UIComponent()
    data class None(
        val message: String,
    ): UIComponent()
}
