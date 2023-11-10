package com.example.themoviedb.screens.user

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class UserViewModel: ViewModel(){
    var selectedImageUri: Uri? by mutableStateOf(null)

    fun updateSelectedImageUri(uri: Uri?) {
        selectedImageUri = uri
    }
}